package integration_tests.controller;

import generate.pdf.MainApplication;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.controller.PdfDownloadController;
import generate.pdf.openpdf.dto.FileResponse;
import integration_tests.BasePostgreSqlContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static integration_tests.Util.setEditorContextForIntegTests;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = MainApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@SqlConfig(encoding = "UTF-8")
public class PdfDownloadControllerIntegTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BasePostgreSqlContainer.getInstance();

    @Autowired
    private PdfDownloadController pdfDownloadController;

    @Autowired
    private StartupConfig startupConfig;

    private Path pdfPath;

    @Test
    public void givenSecurityContextAndRoles_whenAskingForRoles_thenCorrectRolesGetReturned() {
        String filename = "INTEG_TEST.pdf";
        createFile(filename);

        FileResponse file = pdfDownloadController.downloadPdfFile(filename);

        assertNotNull(file);

        removeFile();
    }

    private void createFile(String filename) {
        try {
            setEditorContextForIntegTests();
            pdfPath = Paths.get(startupConfig.getPdfDirectory()).toAbsolutePath().normalize().resolve(filename);
            Files.deleteIfExists(pdfPath);
            Files.createFile(pdfPath);
        } catch (IOException e) {
            fail();
        }
    }

    private void removeFile() {
        try {
            Files.deleteIfExists(pdfPath);
        } catch (IOException e) {
            fail();
        }
    }

}
