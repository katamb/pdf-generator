package generate.pdf.openpdf.service;

import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.FileResponseDto;
import generate.pdf.openpdf.mapper.UserSqlFileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SqlStorageServiceTest {

    private String testFolder;
    private Path pathToTestFolder;
    @Mock
    private StartupConfig startupConfig;
    @Mock
    private UserSqlFileMapper userSqlFileMapper;
    @Mock
    private FileDownloadService fileDownloadService;
    private SqlStorageService sqlStorageService;

    @BeforeEach
    void createDirectories() throws IOException{
        testFolder = UUID.randomUUID().toString();
        pathToTestFolder = Paths.get(testFolder).toAbsolutePath().normalize();
        assumeFalse(Files.exists(pathToTestFolder));
        when(startupConfig.getSqlDirectory()).thenReturn(testFolder);
        sqlStorageService = new SqlStorageService(startupConfig, userSqlFileMapper, fileDownloadService);
        Files.createDirectories(pathToTestFolder);
    }

    @Test
    void givenMockedTestFolder_whenRunningTests_thenFolderGetsCreatedSuccessfully() throws IOException {
        assertTrue(Files.exists(pathToTestFolder));

        deleteCreatedDirectoriesAndFiles();
    }

    @Test
    void givenUsername_whenCreatingNewSqlFile_thenFileWithCorrectNameGetsCreated() throws IOException {
        // Given
        String username = "karl.tamberg";
        // When
        sqlStorageService.createNewSqlForUser(username);
        // Then
        String expectedFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + username + "-0.sql";
        Path targetLocation = pathToTestFolder.resolve(expectedFileName);
        assertTrue(Files.exists(targetLocation));

        verify(userSqlFileMapper, times(1)).insertSqlFileReference(any());

        deleteCreatedDirectoriesAndFiles();
    }

    @Test
    void givenUsernameWhichAllowsPathTraversal_whenCreatingNewSqlFile_thenUsernameGetsEscaped() throws IOException {
        // Given
        String username = "karl/../tambe\\..\\rg";
        // When
        sqlStorageService.createNewSqlForUser(username);
        String brokenFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + username + "-0.sql";
        Path targetLocation = pathToTestFolder.resolve(brokenFileName);
        // Then
        assertFalse("Usernames need to be escaped to avoid path traversal attacks!", Files.exists(targetLocation));
        verify(userSqlFileMapper, times(1)).insertSqlFileReference(any());
        assertEquals(1, Files.list(pathToTestFolder).count());

        deleteCreatedDirectoriesAndFiles();
    }

    @Test
    void givenUsername_whenCreatingMultipleFiles_thenAllFilesGetCreated() throws IOException {
        String username = "karl.tamberg";
        sqlStorageService.createNewSqlForUser(username);
        sqlStorageService.createNewSqlForUser(username);
        sqlStorageService.createNewSqlForUser(username);

        verify(userSqlFileMapper, times(3)).insertSqlFileReference(any());
        assertEquals(3, Files.list(pathToTestFolder).count());

        deleteCreatedDirectoriesAndFiles();
    }

    @Test
    void givenUsernameAndFile_whenDownloadingTheFile_thenTheFileGetsReturned() throws IOException {
        when(fileDownloadService.downloadFile(any(), any())).thenReturn(new FileResponseDto());

        String username = "karl.tamberg";
        sqlStorageService.createNewSqlForUser(username);
        String expectedFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + username + "-0.sql";
        Path targetLocation = pathToTestFolder.resolve(expectedFileName);
        assertTrue(Files.exists(targetLocation));
        FileResponseDto response = sqlStorageService.loadFileAsResource(expectedFileName);
        assertNotNull("This file is supposed to be found!", response);

        deleteCreatedDirectoriesAndFiles();
    }

    void deleteCreatedDirectoriesAndFiles() throws IOException {
        Files.walk(pathToTestFolder)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        assertFalse(
                String.format("Unable to delete directory '%s'!", testFolder),
                Files.exists(pathToTestFolder)
        );
        sqlStorageService = null;
    }

}
