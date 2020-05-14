package integration_tests.mapper;

import generate.pdf.MainApplication;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
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

import static integration_tests.Util.TEST_USERNAME;
import static integration_tests.Util.setEditorContextForIntegTests;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = MainApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@SqlConfig(encoding = "UTF-8")
public class MybatisInterceptorIntegTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BasePostgreSqlContainer.getInstance();

    @Autowired
    private TemplateTextMapper templateTextMapper;

    @Autowired
    private StartupConfig startupConfig;

    @Test
    @Sql(scripts = "classpath:sql/mapper/mybatisInterceptorTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/mapper/removeMybatisInterceptorTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenSecurityContextAndFileAndTextBlock_whenInsertingTextBlock_thenChangesGetSavedToFile() throws IOException {
        Path sqlPath = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath().normalize().resolve(TEST_USERNAME + ".sql");
        Files.deleteIfExists(sqlPath);
        Files.createFile(sqlPath);
        setEditorContextForIntegTests();
        TemplateTextDto textBlock = new TemplateTextDto();
        textBlock.setTextBlockValue("Great new text block");
        templateTextMapper.insertTextBlock(textBlock.getTextBlockValue());

        String fileContent = new String(Files.readAllBytes(sqlPath));
        assertEquals("INSERT INTO pdf_generator.text_block (text_block_value) VALUES ('Great new text block') ON CONFLICT DO NOTHING;\n\n", fileContent);

        Files.deleteIfExists(sqlPath);
    }

    @Test
    @Sql(scripts = "classpath:sql/mapper/mybatisInterceptorTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/mapper/removeMybatisInterceptorTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenSecurityContextAndFileAndTextBlock_whenUpdatingTextBlock_thenChangesGetSavedToFile() throws IOException {
        Path sqlPath = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath().normalize().resolve(TEST_USERNAME + ".sql");
        Files.deleteIfExists(sqlPath);
        Files.createFile(sqlPath);
        setEditorContextForIntegTests();
        TemplateTextDto textBlock = new TemplateTextDto();
        textBlock.setTextBlockValue("New text block");
        textBlock.setHorizontalAlignment(1);
        textBlock.setVerticalAlignment(4);
        textBlock.setLanguageCode(LanguageCode.et);
        textBlock.setTemplateCode(TemplateCode.PRIVATE_SMALL_LOAN_CONTRACT_EE);
        textBlock.setTextBlockName("LOAN_CONTRACT_TEST");
        textBlock.setTextSize(13);

        templateTextMapper.updateTemplateToTextTranslation(textBlock);

        String fileContent = new String(Files.readAllBytes(sqlPath));
        assertEquals(
                "UPDATE pdf_generator.template_text tt SET text_block_id = ( SELECT tb.text_block_id FROM pdf_generator.text_block tb WHERE tb.text_block_value = 'New text block'), text_size = 13.0, horizontal_alignment = 1, vertical_alignment = 4, padding_top = 0, padding_bottom = 0, padding_left = 0, padding_right = 0 WHERE tt.template_code = 'PRIVATE_SMALL_LOAN_CONTRACT_EE' AND tt.language_code = 'et' AND tt.text_block_name = 'LOAN_CONTRACT_TEST';\n\n",
                fileContent
        );

        Files.deleteIfExists(sqlPath);
    }

    @Test
    @Sql(scripts = "classpath:sql/mapper/mybatisInterceptorTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/mapper/removeMybatisInterceptorTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void givenSecurityContextAndFileAndTextBlock_whenSelectingTextBlock_thenChangesDontGetSavedToFile() throws IOException {
        Path sqlPath = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath().normalize().resolve(TEST_USERNAME + ".sql");
        Files.deleteIfExists(sqlPath);
        Files.createFile(sqlPath);
        setEditorContextForIntegTests();

        templateTextMapper.getTextsByTemplateAndLanguage(TemplateCode.PRIVATE_SMALL_LOAN_CONTRACT_EE, LanguageCode.et);

        String fileContent = new String(Files.readAllBytes(sqlPath));
        assertEquals("", fileContent);

        Files.deleteIfExists(sqlPath);
    }

}
