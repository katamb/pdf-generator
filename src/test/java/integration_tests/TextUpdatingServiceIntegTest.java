package integration_tests;

import generate.pdf.MainApplication;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = MainApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@SqlConfig(encoding = "UTF-8")
public class TextUpdatingServiceIntegTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BasePostgreSqlContainer.getInstance();

    @Autowired
    private TemplateTextMapper templateTextMapper;

    @Test
    @Transactional
    public void givenUsersInDB_WhenUpdateStatusForNameModifyingQueryAnnotationJPQL_ThenModifyMatchingUsers() {
        TemplateTextBlock textBlock = new TemplateTextBlock();
        textBlock.setTextBlockValue("Test value.");
        assertTrue(templateTextMapper.findAllAvailableTemplates().size() > 0);
    }

}
