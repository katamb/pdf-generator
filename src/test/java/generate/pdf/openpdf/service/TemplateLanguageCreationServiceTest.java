package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateLanguageCreationServiceTest {

    @Mock
    private TemplateTextMapper templateTextMapper;
    @InjectMocks
    private TemplateLanguageCreationService templateLanguageCreationService;

    @Test
    void testCreateNewLanguageForTemplateValidation() {
        when(templateTextMapper.getTextsByTemplateAndLanguage(any(), any())).thenReturn(Collections.emptyList());

        assertThrows(BadRequestException.class, () -> templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.TEMPLATE_CODE_FOR_TESTING, LanguageCode.XX, LanguageCode.XX
        ));
        assertThrows(BadRequestException.class, () -> templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.TEMPLATE_CODE_FOR_TESTING, LanguageCode.XX, LanguageCode.YY
        ));
    }

    @Test
    void testCreateNewLanguageForTemplateValidationNewLanguageExists() {
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.TEMPLATE_CODE_FOR_TESTING.toString(), LanguageCode.XX.toString()))
                .thenReturn(Collections.singletonList(new TemplateTextBlock()));
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.TEMPLATE_CODE_FOR_TESTING.toString(), LanguageCode.YY.toString()))
                .thenReturn(Collections.singletonList(new TemplateTextBlock()));

        assertThrows(BadRequestException.class, () -> templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.TEMPLATE_CODE_FOR_TESTING, LanguageCode.XX, LanguageCode.XX
        ));
        assertThrows(BadRequestException.class, () -> templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.TEMPLATE_CODE_FOR_TESTING, LanguageCode.XX, LanguageCode.YY
        ));
    }

    @Test
    void testCreateNewLanguageForTemplateValidationNoNewLanguageExists() {
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.TEMPLATE_CODE_FOR_TESTING.toString(), LanguageCode.XX.toString()))
                .thenReturn(Collections.singletonList(new TemplateTextBlock()));
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.TEMPLATE_CODE_FOR_TESTING.toString(), LanguageCode.YY.toString()))
                .thenReturn(Collections.emptyList());

        assertThrows(BadRequestException.class, () -> templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.TEMPLATE_CODE_FOR_TESTING, LanguageCode.XX, LanguageCode.XX
        ));
        // Next one shouldn't throw anything
        templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.TEMPLATE_CODE_FOR_TESTING, LanguageCode.XX, LanguageCode.YY);
    }

    @Test
    void testCreateNewLanguageForTemplateEverythingCorrect() {
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.TEMPLATE_CODE_FOR_TESTING.toString(), LanguageCode.XX.toString()))
                .thenReturn(Collections.singletonList(new TemplateTextBlock()));
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.TEMPLATE_CODE_FOR_TESTING.toString(), LanguageCode.YY.toString()))
                .thenReturn(Collections.emptyList());

        templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.TEMPLATE_CODE_FOR_TESTING, LanguageCode.XX, LanguageCode.YY);
        verify(templateTextMapper, times(1)).batchInsert(any());
    }

}
