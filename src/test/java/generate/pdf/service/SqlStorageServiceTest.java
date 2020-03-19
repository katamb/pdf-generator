package generate.pdf.service;

import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.mapper.UserSqlFileMapper;
import generate.pdf.openpdf.service.SqlStorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SqlStorageServiceTest {

    private SqlStorageService sqlStorageService;
    private String testFolder = "test_sql_files/";
    private Path pathToTestFolder;
    @Mock
    private StartupConfig startupConfig;
    @Mock
    private UserSqlFileMapper userSqlFileMapper;

    @BeforeEach
    void initUseCase() {
        when(startupConfig.getSqlDirectory()).thenReturn(testFolder);
        pathToTestFolder = Paths.get(testFolder).toAbsolutePath().normalize();
        sqlStorageService = new SqlStorageService(startupConfig, userSqlFileMapper);
    }

    @AfterEach
    void cleanup() {
        pathToTestFolder.toFile().delete();
    }

    @Test
    void testTestDirectoryGetsCreated() {
        assertTrue(Files.exists(pathToTestFolder));
    }

}
