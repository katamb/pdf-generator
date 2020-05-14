package integration_tests.controller;

import generate.pdf.MainApplication;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.controller.UserController;
import generate.pdf.openpdf.dto.UserSqlFileDto;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.mapper.UserSqlFileMapper;
import integration_tests.BasePostgreSqlContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static integration_tests.Util.TEST_USERNAME;
import static integration_tests.Util.getUserPrincipal;
import static integration_tests.Util.setEditorContextForIntegTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = MainApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@SqlConfig(encoding = "UTF-8")
public class UserControllerIntegTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BasePostgreSqlContainer.getInstance();

    @Autowired
    private UserSqlFileMapper userSqlFileMapper;

    @Autowired
    private UserController userController;

    @Autowired
    private StartupConfig startupConfig;

    @Test
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetupTeardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenSecurityContextAndRoles_whenAskingForRoles_thenCorrectRolesGetReturned() {
        setEditorContextForIntegTests();

        List<String> expectedRoles = Arrays.asList("ROLE_USER", "ROLE_EDITOR");
        List<String> givenRoles = userController.findUserRoles(getUserPrincipal());
        assertTrue("All roles not granted!", givenRoles.containsAll(expectedRoles));
    }

    @Test
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetupTeardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenSecurityContextAndRoles_whenCreatingSqlFiles_thenAllFilesGetCreated() throws IOException {
        setEditorContextForIntegTests();

        userController.addSqlFile(getUserPrincipal());
        userController.addSqlFile(getUserPrincipal());

        String expectedFirstFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "-INTEG_TEST-0.sql";
        Path firstFile = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath().normalize().resolve(expectedFirstFileName);
        assertTrue("First file not created!", Files.exists(firstFile));

        String expectedSecondFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "-INTEG_TEST-1.sql";
        Path secondFile = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath().normalize().resolve(expectedSecondFileName);
        assertTrue("Second file not created!", Files.exists(secondFile));

        assertEquals(2, userSqlFileMapper.getUserFiles(TEST_USERNAME).size());

        // Cleanup
        Files.deleteIfExists(firstFile);
        Files.deleteIfExists(secondFile);
    }

    @Test
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetupTeardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenUnselectedFiles_whenSelectingSqlFiles_thenOnlyOneFileIsSelected() throws IOException {
        setEditorContextForIntegTests();
        userController.addSqlFile(getUserPrincipal());
        userController.addSqlFile(getUserPrincipal());
        String expectedFirstFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "-INTEG_TEST-0.sql";
        Path firstFile = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath()
                .normalize().resolve(expectedFirstFileName);
        String expectedSecondFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "-INTEG_TEST-1.sql";
        Path secondFile = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath()
                .normalize().resolve(expectedSecondFileName);

        List<UserSqlFileDto> sqlFiles = userSqlFileMapper.getUserFiles(TEST_USERNAME);
        userController.selectSqlFile(getUserPrincipal(), sqlFiles.get(0).getId());
        Long firstSelectedFileId = userController.getSelectedSqlFile(getUserPrincipal()).getId();
        userController.selectSqlFile(getUserPrincipal(), sqlFiles.get(1).getId());
        Long secondSelectedFileId = userController.getSelectedSqlFile(getUserPrincipal()).getId();

        assertTrue("Selecting file should deselect previous selection!", !firstSelectedFileId.equals(secondSelectedFileId));

        // Cleanup
        Files.deleteIfExists(firstFile);
        Files.deleteIfExists(secondFile);
    }

    @Test
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetupTeardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenNoFiles_whenDownloadingSelectedFile_thenErrorIsThrown() {
        setEditorContextForIntegTests();
        assertThrows(BadRequestException.class,
                () -> userController.downloadSelectedSqlFile(getUserPrincipal()));
    }

    @Test
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/controller/userControllerTestSetupTeardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenSelectedFile_whenDownloadingSqlFiles_thenFileIsReturned() throws IOException {
        setEditorContextForIntegTests();
        userController.addSqlFile(getUserPrincipal());
        String expectedFirstFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "-INTEG_TEST-0.sql";
        Path firstFile = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath()
                .normalize().resolve(expectedFirstFileName);
        List<UserSqlFileDto> sqlFiles = userSqlFileMapper.getUserFiles(TEST_USERNAME);
        userController.selectSqlFile(getUserPrincipal(), sqlFiles.get(0).getId());

        assertNotNull("File should be returned!",
                userController.getSqlFile(getUserPrincipal(), expectedFirstFileName));

        // Cleanup
        Files.deleteIfExists(firstFile);
    }

}
