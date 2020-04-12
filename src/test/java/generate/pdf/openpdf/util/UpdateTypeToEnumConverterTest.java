package generate.pdf.openpdf.util;

import generate.pdf.openpdf.enums.UpdateType;
import generate.pdf.openpdf.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UpdateTypeToEnumConverterTest {

    @InjectMocks
    private UpdateTypeToEnumConverter updateTypeToEnumConverter;

    @Test
    void testUpdateTypeToEnumConverter() {
        assertEquals(UpdateType.UPDATE_ONLY_CURRENT, updateTypeToEnumConverter.convert("UPDATE_ONLY_CURRENT"));
        assertEquals(UpdateType.CONFIRM_UPDATE, updateTypeToEnumConverter.convert("CONFIRM_UPDATE"));
        assertEquals(UpdateType.UPDATE_ALL, updateTypeToEnumConverter.convert("UPDATE_ALL"));
        assertEquals(UpdateType.UPDATE_ONLY_CURRENT, updateTypeToEnumConverter.convert("update_only_current"));
        assertEquals(UpdateType.CONFIRM_UPDATE, updateTypeToEnumConverter.convert("confirm_update"));
        assertEquals(UpdateType.UPDATE_ALL, updateTypeToEnumConverter.convert("update_all"));
    }

    @Test
    void testUpdateTypeToEnumConverterThrowsError() {
        assertThrows(BadRequestException.class,
                () -> updateTypeToEnumConverter.convert("UPDATE"));
    }

}
