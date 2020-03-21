package generate.pdf.openpdf.service.printout;

import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PrintoutGeneratorProviderTest {

    @Mock
    private StartupConfig startupConfig;

    @Test
    void testGetPrintoutGeneratorForGivenPrintoutType() {
        DummyPrintoutGenerator dummyPrintoutGenerator = new DummyPrintoutGenerator(startupConfig);
        PrintoutGeneratorProvider printoutGeneratorProvider =
                new PrintoutGeneratorProvider(Collections.singletonList(dummyPrintoutGenerator));

        PdfGenerator providedGenerator =
                printoutGeneratorProvider.getPrintoutGeneratorForGivenPrintoutType(TemplateCode.DUMMY_TEMPLATE_FOR_TESTING);

        assertEquals(dummyPrintoutGenerator, providedGenerator);
    }

    @Test
    void testGetPrintoutGeneratorForGivenPrintoutTypeThrowsError() {
        PrintoutGeneratorProvider printoutGeneratorProvider = new PrintoutGeneratorProvider(Collections.emptyList());


        assertThrows(BadRequestException.class, () ->
                printoutGeneratorProvider.getPrintoutGeneratorForGivenPrintoutType(TemplateCode.DUMMY_TEMPLATE_FOR_TESTING));
    }

}
