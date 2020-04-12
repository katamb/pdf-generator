package generate.pdf.openpdf.util;

import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TemplateCodeToEnumConverterTest {

    @InjectMocks
    private TemplateCodeToEnumConverter templateCodeToEnumConverter;

    @Test
    void testTemplateCodeToEnumConverter() {
        assertEquals(TemplateCode.EDITABLE_FORM_EE, templateCodeToEnumConverter.convert("EDITABLE_FORM_EE"));
        assertEquals(TemplateCode.EDITABLE_FORM_EE, templateCodeToEnumConverter.convert("EDITABLE_form_EE"));
        assertEquals(TemplateCode.EDITABLE_FORM_EE, templateCodeToEnumConverter.convert("EDITAbLE_FORM_EE"));
        assertEquals(TemplateCode.EDITABLE_FORM_EE, templateCodeToEnumConverter.convert("editable_form_ee"));
    }

    @Test
    void testTemplateCodeToEnumConverterThrowsError() {
        assertThrows(BadRequestException.class,
                () -> templateCodeToEnumConverter.convert("DUMMY"));
    }

}
