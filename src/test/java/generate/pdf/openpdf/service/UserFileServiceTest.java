package generate.pdf.openpdf.service;

import generate.pdf.openpdf.security.AppUser;
import generate.pdf.openpdf.dto.ResponseWithMessageDto;
import generate.pdf.openpdf.dto.UserSqlFileDto;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.exception.UnauthorizedException;
import generate.pdf.openpdf.mapper.UserSqlFileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFileServiceTest {

    private static final String TEST_EMAIL = "katamb@taltech.ee";

    private UsernamePasswordAuthenticationToken principal;
    private UserSqlFileDto file1;
    private UserSqlFileDto file2;
    private UserSqlFileDto file3;
    @Mock
    private UserSqlFileMapper userSqlFileMapper;
    @Mock
    private SqlStorageService sqlStorageService;
    @InjectMocks
    private UserFileService userFileService;

    @BeforeEach
    void givenUserPrincipalAndThreeUserFiles() {
        AppUser user = new AppUser(TEST_EMAIL, "unit_test",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        principal = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        file1 = new UserSqlFileDto();
        file1.setUsername(TEST_EMAIL);
        file1.setSqlFileName("test_file_1.sql");
        file1.setSelected(false);
        file1.setCreatedAt(LocalDateTime.now());

        file2 = new UserSqlFileDto();
        file2.setUsername(TEST_EMAIL);
        file2.setSqlFileName("test_file_2.sql");
        file2.setSelected(true);
        file2.setCreatedAt(LocalDateTime.now().plusDays(1));

        file3 = new UserSqlFileDto();
        file3.setUsername(TEST_EMAIL);
        file3.setSqlFileName("test_file_3.sql");
        file3.setSelected(false);
        file3.setCreatedAt(LocalDateTime.now().plusDays(2));
    }

    @Test
    void testGetUserEmail() {
        ResponseWithMessageDto response = userFileService.getUserEmail(principal);
        assertEquals(TEST_EMAIL, response.getMessage());
    }

    @Test
    void testGetUserEmailFails() {
        assertThrows(UnauthorizedException.class, () -> userFileService.getUserEmail(null));
    }

    @Test
    void testGetSelectedSqlFile() {
        List<UserSqlFileDto> files = Arrays.asList(file1, file2, file3);
        when(userSqlFileMapper.getUserFiles(TEST_EMAIL)).thenReturn(files);

        UserSqlFileDto file = userFileService.getSelectedSqlFile(principal);
        assertEquals(file2, file);
    }

    @Test
    void testGetSelectedSqlFileNoneSelected() {
        List<UserSqlFileDto> files = Arrays.asList(file1, file3);
        when(userSqlFileMapper.getUserFiles(TEST_EMAIL)).thenReturn(files);

        assertThrows(BadRequestException.class, () -> userFileService.getSelectedSqlFile(principal));
    }

    @Test
    void testGetSqlFilesInCorrectOrder() {
        List<UserSqlFileDto> files = Arrays.asList(file1, file2, file3);
        when(userSqlFileMapper.getUserFiles(TEST_EMAIL)).thenReturn(files);

        assertEquals(Arrays.asList(file3, file2, file1), userFileService.getUserSqlFiles(principal));
    }

    @Test
    void testUserCanOnlyDownloadOwnFiles() {
        List<UserSqlFileDto> files = Arrays.asList(file1, file2, file3);
        when(userSqlFileMapper.getUserFiles(TEST_EMAIL)).thenReturn(files);

        assertThrows(BadRequestException.class, () -> userFileService.downloadFile(principal, "some_other_file.sql"));
    }

}
