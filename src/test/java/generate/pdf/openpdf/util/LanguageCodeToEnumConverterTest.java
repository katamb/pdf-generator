package generate.pdf.openpdf.util;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LanguageCodeToEnumConverterTest {

    @InjectMocks
    private LanguageCodeToEnumConverter languageCodeToEnumConverter;

    @Test
    void testLanguageCodeToEnumConverter() {
        assertEquals(LanguageCode.et, languageCodeToEnumConverter.convert("et"));
        assertEquals(LanguageCode.et, languageCodeToEnumConverter.convert("eT"));
        assertEquals(LanguageCode.et, languageCodeToEnumConverter.convert("Et"));
        assertEquals(LanguageCode.et, languageCodeToEnumConverter.convert("ET"));
    }

    @Test
    void testLanguageCodeToEnumConverterThrowsError() {
        assertThrows(BadRequestException.class,
                () -> languageCodeToEnumConverter.convert("xx"));
    }

}
