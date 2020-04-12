package integration_tests.controller;

import generate.pdf.MainApplication;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.controller.PdfEditingController;
import generate.pdf.openpdf.dto.ResponseWithMessage;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.enums.UpdateType;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import integration_tests.BasePostgreSqlContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static generate.pdf.openpdf.enums.TemplateCode.EDITABLE_FORM_EE;
import static integration_tests.Util.TEST_USERNAME;
import static integration_tests.Util.setEditorContextForIntegTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = MainApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@SqlConfig(encoding = "UTF-8")
public class PdfEditingControllerIntegTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BasePostgreSqlContainer.getInstance();

    @Autowired
    private StartupConfig startupConfig;

    @Autowired
    private TemplateTextMapper templateTextMapper;

    @Autowired
    private PdfEditingController pdfEditingController;

    private Path sqlPath;

    @Test
    public void givenExistingTemplate_whenAskingForLanguages_thenNonEmptyListIsReturned() {
        assertFalse("When no templates created, then list needs to be empty!",
                pdfEditingController.getTemplateLanguages(EDITABLE_FORM_EE).isEmpty());
    }

    @Test
    public void givenExistingLanguage_whenAskingForLanguageName_thenCorrectLanguageIsReturned() {
        assertEquals("Estonian", pdfEditingController.getLanguageName(LanguageCode.et).getText());
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/controller/removeEditableFormPlSetup.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetup.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/controller/removeEditableFormPlSetup.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetupTeardown.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenExistingTemplateAndLanguage_whenNewLanguageDoesntExist_thenNewLanguageIsCreated() {
        createFileForInterceptor();

        List<TemplateTextBlock> entries =
                templateTextMapper.getTextsByTemplateAndLanguage(TemplateCode.EDITABLE_FORM_EE.toString(), LanguageCode.pl.toString());
        assertTrue(entries.isEmpty());

        pdfEditingController.createNewLanguageForTemplate(TemplateCode.EDITABLE_FORM_EE, LanguageCode.et, LanguageCode.pl);

        entries = templateTextMapper.getTextsByTemplateAndLanguage(TemplateCode.EDITABLE_FORM_EE.toString(), LanguageCode.pl.toString());
        assertFalse(entries.isEmpty());

        removeInterceptorFile();
    }

    @Test
    public void givenExistingTemplateAndLanguage_whenNewLanguageAndOldLanguageAreTheSame_thenErrorIsThrown() {
        assertThrows(BadRequestException.class,
                () -> pdfEditingController.createNewLanguageForTemplate(TemplateCode.EDITABLE_FORM_EE, LanguageCode.et, LanguageCode.et));
    }

    @Test
    @Sql(scripts = "classpath:sql/controller/removeEditableFormPlSetup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void givenTemplateAndLanguage_whenExistingLanguageDoesntActuallyExists_thenErrorIsThrown() {
        assertThrows(BadRequestException.class,
                () -> pdfEditingController.createNewLanguageForTemplate(TemplateCode.EDITABLE_FORM_EE, LanguageCode.pl, LanguageCode.et));
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/controller/createTextBlock.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetup.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/controller/removeTextBlock.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetupTeardown.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenConfirmUpdateTypeAndTextBlock_whenUpdatingTextBlock_thenMultipleChoicesReturned() {
        createFileForInterceptor();

        Long textBlockValueId = templateTextMapper.findTextBlockIdByValue("qqxx TEST_BLOCK_1 xxqq");
        TemplateTextBlock templateTextBlock = templateTextMapper.findTextBlocksById(textBlockValueId).get(0);
        templateTextBlock.setTextBlockValue("qqyxx TEST_BLOCK_1 xxyqq");
        ResponseWithMessage response = pdfEditingController.updateTextBlock(UpdateType.CONFIRM_UPDATE, templateTextBlock);

        assertEquals((Integer) HttpStatus.MULTIPLE_CHOICES.value(), response.getStatusCode());

        removeInterceptorFile();
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/controller/createTextBlock.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetup.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/controller/removeTextBlock.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetupTeardown.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void givenUpdateAllTypeAndTextBlock_whenUpdatingTextBlockToExistingValue_thenNoNewTextBlockCreated() {
        createFileForInterceptor();

        Long textBlockValueId = templateTextMapper.findTextBlockIdByValue("qqxx TEST_BLOCK_1 xxqq");
        TemplateTextBlock templateTextBlock = templateTextMapper.findTextBlocksById(textBlockValueId).get(0);
        templateTextBlock.setTextBlockValue("qqxx TEST_BLOCK_2 xxqq");
        templateTextBlock.setPreviousTextBlockValue("qqxx TEST_BLOCK_1 xxqq");
        ResponseWithMessage response = pdfEditingController.updateTextBlock(UpdateType.UPDATE_ALL, templateTextBlock);

        assertEquals((Integer) HttpStatus.OK.value(), response.getStatusCode());

        Long textBlock2ValueId = templateTextMapper.findTextBlockIdByValue("qqxx TEST_BLOCK_2 xxqq");
        List<TemplateTextBlock> templateTextBlock2 = templateTextMapper.findTextBlocksById(textBlock2ValueId);
        assertEquals(2, templateTextBlock2.size());
        List<TemplateTextBlock> templateTextBlock1 = templateTextMapper.findTextBlocksById(textBlockValueId);
        assertEquals(0, templateTextBlock1.size());

        removeInterceptorFile();
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/controller/createTextBlock.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetup.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/controller/removeTextBlock.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetupTeardown.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void givenUpdateCurrentTypeAndTextBlock_whenUpdatingTextBlockToExistingValue_thenNoNewTextBlockCreated() {
        createFileForInterceptor();

        Long textBlockValueId = templateTextMapper.findTextBlockIdByValue("qqxx TEST_BLOCK_1 xxqq");
        TemplateTextBlock templateTextBlock = templateTextMapper.findTextBlocksById(textBlockValueId).get(0);
        templateTextBlock.setTextBlockValue("qqxx TEST_BLOCK_2 xxqq");
        ResponseWithMessage response = pdfEditingController.updateTextBlock(UpdateType.UPDATE_ONLY_CURRENT, templateTextBlock);

        assertEquals((Integer) HttpStatus.OK.value(), response.getStatusCode());

        Long textBlock2ValueId = templateTextMapper.findTextBlockIdByValue("qqxx TEST_BLOCK_2 xxqq");
        List<TemplateTextBlock> templateTextBlock2 = templateTextMapper.findTextBlocksById(textBlock2ValueId);
        assertEquals(1, templateTextBlock2.size());
        List<TemplateTextBlock> templateTextBlock1 = templateTextMapper.findTextBlocksById(textBlockValueId);
        assertEquals(1, templateTextBlock1.size());

        removeInterceptorFile();
    }

    @Test
    @Sql(scripts = {
            "classpath:sql/controller/createTextBlock.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetup.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:sql/controller/removeTextBlock.sql",
            "classpath:sql/controller/pdfEditingControllerTestSetupTeardown.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void givenUpdateAllTypeAndTextBlock_whenUpdatingTextBlockToNonExistingValue_thenNewTextBlockCreated() {
        createFileForInterceptor();

        Long textBlockValueId = templateTextMapper.findTextBlockIdByValue("qqxx TEST_BLOCK_1 xxqq");
        TemplateTextBlock templateTextBlock = templateTextMapper.findTextBlocksById(textBlockValueId).get(0);
        templateTextBlock.setTextBlockValue("qqxx TEST_BLOCK_3 xxqq");
        templateTextBlock.setPreviousTextBlockValue("qqxx TEST_BLOCK_1 xxqq");
        ResponseWithMessage response = pdfEditingController.updateTextBlock(UpdateType.UPDATE_ALL, templateTextBlock);

        assertEquals((Integer) HttpStatus.OK.value(), response.getStatusCode());

        Long textBlock2ValueId = templateTextMapper.findTextBlockIdByValue("qqxx TEST_BLOCK_3 xxqq");
        List<TemplateTextBlock> templateTextBlock2 = templateTextMapper.findTextBlocksById(textBlock2ValueId);
        assertEquals(2, templateTextBlock2.size());
        List<TemplateTextBlock> templateTextBlock1 = templateTextMapper.findTextBlocksById(textBlockValueId);
        assertEquals(0, templateTextBlock1.size());

        removeInterceptorFile();
    }

    private void createFileForInterceptor() {
        try {
            setEditorContextForIntegTests();
            sqlPath = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath().normalize().resolve(TEST_USERNAME + ".sql");
            Files.deleteIfExists(sqlPath);
            Files.createFile(sqlPath);
        } catch (IOException e) {
            fail();
        }
    }

    private void removeInterceptorFile() {
        try {
            Files.deleteIfExists(sqlPath);
        } catch (IOException e) {
            fail();
        }
    }

}
