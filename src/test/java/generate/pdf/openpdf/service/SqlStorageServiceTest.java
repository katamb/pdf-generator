package generate.pdf.openpdf.service;

import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.exception.BadRequestException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@ExtendWith(MockitoExtension.class)
class SqlStorageServiceTest {

    private String testFolder;
    private Path pathToTestFolder;
    @Mock
    private StartupConfig startupConfig;
    @Mock
    private UserSqlFileMapper userSqlFileMapper;
    private SqlStorageService sqlStorageService;

    @BeforeEach
    void initUseCase() {
        testFolder = UUID.randomUUID().toString();
        pathToTestFolder = Paths.get(testFolder).toAbsolutePath().normalize();
        assumeFalse(Files.exists(pathToTestFolder));
        when(startupConfig.getSqlDirectory()).thenReturn(testFolder);
        sqlStorageService = new SqlStorageService(startupConfig, userSqlFileMapper);
    }

    @Test
    void testTestDirectoryGetsCreated() throws IOException {
        assertTrue(Files.exists(pathToTestFolder));

        cleanup();
    }

    @Test
    void testCreateSqlFile() throws IOException {
        String username = "karl.tamberg";
        sqlStorageService.createNewSqlForUser(username);
        String expectedFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + username + "-0.sql";
        Path targetLocation = pathToTestFolder.resolve(expectedFileName);
        assertTrue(Files.exists(targetLocation));

        verify(userSqlFileMapper, times(1)).insertSqlFileReference(any());

        cleanup();
    }

    @Test
    void testCreateSqlFileSketchyUsername() throws IOException {
        String username = "karl/../tambe\\..\\rg";
        sqlStorageService.createNewSqlForUser(username);
        String brokenFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + username + "-0.sql";
        Path targetLocation = pathToTestFolder.resolve(brokenFileName);

        assertFalse("Usernames need to be escaped to avoid path traversal attacks!", Files.exists(targetLocation));
        verify(userSqlFileMapper, times(1)).insertSqlFileReference(any());
        assertEquals(1, Files.list(pathToTestFolder).count());

        cleanup();
    }

    @Test
    void testCreateMultipleSqlFilesSameUser() throws IOException {
        String username = "karl.tamberg";
        sqlStorageService.createNewSqlForUser(username);
        sqlStorageService.createNewSqlForUser(username);
        sqlStorageService.createNewSqlForUser(username);

        verify(userSqlFileMapper, times(3)).insertSqlFileReference(any());
        assertEquals(3, Files.list(pathToTestFolder).count());

        cleanup();
    }

    @Test
    void testLoadFileAsResourceFileNotFound() throws IOException {
        String randomFilename = "randomname.sql";
        assertThrows(BadRequestException.class, () -> sqlStorageService.loadFileAsResource(randomFilename));

        cleanup();
    }

    @Test
    void testLoadFileAsResourceFileIsFound() throws IOException {
        String username = "karl.tamberg";
        sqlStorageService.createNewSqlForUser(username);
        String expectedFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "-" + username + "-0.sql";
        Path targetLocation = pathToTestFolder.resolve(expectedFileName);
        assertTrue(Files.exists(targetLocation));
        sqlStorageService.loadFileAsResource(expectedFileName);

        cleanup();
    }

    void cleanup() throws IOException {
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
