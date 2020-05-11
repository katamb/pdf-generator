package integration_tests.enums;

import generate.pdf.MainApplication;
import generate.pdf.openpdf.dto.ValueTextComboDto;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import integration_tests.BasePostgreSqlContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = MainApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@SqlConfig(encoding = "UTF-8")
public class EnumMappingsToDatabaseIntegTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = BasePostgreSqlContainer.getInstance();

    @Autowired
    private TemplateTextMapper templateTextMapper;

    @Test
    public void givenLanguageCodeEnums_whenReadingLanguageCodesFromDatabase_thenAllLanguageCodesInDatabaseHaveMappingInEnum() {
        List<LanguageCode> languageEnums = Arrays.asList(LanguageCode.class.getEnumConstants());
        List<LanguageCode> languageEnumsFromDb = templateTextMapper.findAllAvailableLanguages()
                .stream()
                .map(ValueTextComboDto::getValue)
                .map(LanguageCode::valueOf)
                .collect(Collectors.toList());
        assertTrue(languageEnums.containsAll(languageEnumsFromDb));
    }

    @Test
    public void givenTemplateCodeEnums_whenReadingTemplateCodesFromDatabase_thenAllTemplateCodesInDatabaseHaveMappingInEnum() {
        List<TemplateCode> templateCodes = Arrays.asList(TemplateCode.class.getEnumConstants());
        List<TemplateCode> templateCodesFromDb = templateTextMapper.findAllAvailableTemplates()
                .stream()
                .map(TemplateCode::valueOf)
                .collect(Collectors.toList());
        assertTrue(templateCodes.containsAll(templateCodesFromDb));
    }

}
