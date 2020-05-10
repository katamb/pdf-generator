package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    void givenNoBaseTemplate_whenCreatingNewTemplateOffOfNonExistentBaseTemplate_thenValidationFails() {
        when(templateTextMapper.getTextsByTemplateAndLanguage(any(), any())).thenReturn(Collections.emptyList());

        assertThrows(BadRequestException.class, () -> templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.EDITABLE_FORM_EE, LanguageCode.et, LanguageCode.en
        ));
    }

    @Test
    void givenTemplateInTwoLanguages_whenCreatingTemplateThatAlreadyExistsInThatLanguage_thenValidationFails() {
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.EDITABLE_FORM_EE.toString(), LanguageCode.et.toString()))
                .thenReturn(Collections.singletonList(new TemplateTextBlock()));
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.EDITABLE_FORM_EE.toString(), LanguageCode.en.toString()))
                .thenReturn(Collections.singletonList(new TemplateTextBlock()));

        assertThrows(BadRequestException.class, () -> templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.EDITABLE_FORM_EE, LanguageCode.et, LanguageCode.et
        ));
        assertThrows(BadRequestException.class, () -> templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.EDITABLE_FORM_EE, LanguageCode.et, LanguageCode.en
        ));
    }

    @Test
    void givenTemplateInEstonian_whenCreatingTemplateInEnglishOffOfEstonianVersion_thenNoExceptionsThrown() {
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.EDITABLE_FORM_EE.toString(), LanguageCode.et.toString()))
                .thenReturn(Collections.singletonList(new TemplateTextBlock()));
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.EDITABLE_FORM_EE.toString(), LanguageCode.en.toString()))
                .thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> templateLanguageCreationService
                .createNewLanguageForTemplate(TemplateCode.EDITABLE_FORM_EE, LanguageCode.et, LanguageCode.en));
    }

    @Test
    void givenTemplateInEstonian_whenCreatingTemplateInEnglishOffOfEstonianVersion_thenCorrectMethodsGetCalled() {
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.EDITABLE_FORM_EE.toString(), LanguageCode.et.toString()))
                .thenReturn(Collections.singletonList(new TemplateTextBlock()));
        when(templateTextMapper
                .getTextsByTemplateAndLanguage(TemplateCode.EDITABLE_FORM_EE.toString(), LanguageCode.en.toString()))
                .thenReturn(Collections.emptyList());

        templateLanguageCreationService.createNewLanguageForTemplate(
                TemplateCode.EDITABLE_FORM_EE, LanguageCode.et, LanguageCode.en);
        verify(templateTextMapper, times(1)).batchInsert(any());
    }

}
