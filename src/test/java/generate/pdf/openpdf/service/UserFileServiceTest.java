package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.ResponseWithMessage;
import generate.pdf.openpdf.dto.UserSqlFile;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.exception.UnauthorizedException;
import generate.pdf.openpdf.mapper.UserSqlFileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFileServiceTest {

    private static final String TEST_EMAIL = "katamb@taltech.ee";

    private OAuth2AuthenticationToken principal;
    private UserSqlFile file1;
    private UserSqlFile file2;
    private UserSqlFile file3;
    @Mock
    private UserSqlFileMapper userSqlFileMapper;
    @Mock
    private SqlStorageService sqlStorageService;
    @InjectMocks
    private UserFileService userFileService;

    @BeforeEach
    void initUseCase() {
        OAuth2User user = new OAuth2User() {
            @Override
            public Map<String, Object> getAttributes() {
                HashMap<String, Object> attributes = new HashMap<>();
                attributes.put("email", TEST_EMAIL);
                return attributes;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        };
        principal = new OAuth2AuthenticationToken(user, null, "123456789");

        file1 = new UserSqlFile();
        file1.setUsername(TEST_EMAIL);
        file1.setSqlFileName("test_file_1.sql");
        file1.setSelected(false);
        file1.setCreatedAt(LocalDateTime.now());

        file2 = new UserSqlFile();
        file2.setUsername(TEST_EMAIL);
        file2.setSqlFileName("test_file_2.sql");
        file2.setSelected(true);
        file2.setCreatedAt(LocalDateTime.now().plusDays(1));

        file3 = new UserSqlFile();
        file3.setUsername(TEST_EMAIL);
        file3.setSqlFileName("test_file_3.sql");
        file3.setSelected(false);
        file3.setCreatedAt(LocalDateTime.now().plusDays(2));
    }

    @Test
    void testGetUserEmail() {
        ResponseWithMessage response = userFileService.getUserEmail(principal);
        assertEquals(TEST_EMAIL, response.getMessage());
    }

    @Test
    void testGetUserEmailFails() {
        assertThrows(UnauthorizedException.class, () -> userFileService.getUserEmail(null));
    }

    @Test
    void testGetSelectedSqlFile() {
        List<UserSqlFile> files = Arrays.asList(file1, file2, file3);
        when(userSqlFileMapper.getUserFiles(TEST_EMAIL)).thenReturn(files);

        UserSqlFile file = userFileService.getSelectedSqlFile(principal);
        assertEquals(file2, file);
    }

    @Test
    void testGetSelectedSqlFileNoneSelected() {
        List<UserSqlFile> files = Arrays.asList(file1, file3);
        when(userSqlFileMapper.getUserFiles(TEST_EMAIL)).thenReturn(files);

        assertThrows(BadRequestException.class, () -> userFileService.getSelectedSqlFile(principal));
    }

    @Test
    void testGetSqlFilesInCorrectOrder() {
        List<UserSqlFile> files = Arrays.asList(file1, file2, file3);
        when(userSqlFileMapper.getUserFiles(TEST_EMAIL)).thenReturn(files);

        assertEquals(Arrays.asList(file3, file2, file1), userFileService.getUserSqlFiles(principal));
    }

    @Test
    void testUserCanOnlyDownloadOwnFiles() {
        List<UserSqlFile> files = Arrays.asList(file1, file2, file3);
        when(userSqlFileMapper.getUserFiles(TEST_EMAIL)).thenReturn(files);

        assertThrows(BadRequestException.class, () -> userFileService.downloadFile(principal, "some_other_file.sql"));
    }

}