package generate.pdf.openpdf.service.table;

import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.service.NumberingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumberingServiceTest {

    @InjectMocks
    private NumberingService numberingService;

    @Test
    void givenNumberedTextBlocks_whenNumberingBlocks_thenNumberingIsCorrect() {
        TemplateTextBlock block1 = new TemplateTextBlock();
        block1.setNumbering(true);
        block1.setNumberingLevel(1);
        TemplateTextBlock block2 = new TemplateTextBlock();
        block2.setNumbering(true);
        block2.setNumberingLevel(1);
        TemplateTextBlock block3 = new TemplateTextBlock();
        block3.setNumbering(true);
        block3.setNumberingLevel(2);
        TemplateTextBlock block4 = new TemplateTextBlock();
        block4.setNumbering(true);
        block4.setNumberingLevel(1);
        LinkedList<Integer> memory = new LinkedList<>();

        String result = numberingService.getNumberForTextBlock(block1, memory);
        assertEquals("1.", result);
        result = numberingService.getNumberForTextBlock(block2, memory);
        assertEquals("2.", result);
        result = numberingService.getNumberForTextBlock(block3, memory);
        assertEquals("2.1.", result);
        result = numberingService.getNumberForTextBlock(block4, memory);
        assertEquals("3.", result);
    }

}
