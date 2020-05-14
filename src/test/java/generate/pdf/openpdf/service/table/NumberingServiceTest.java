package generate.pdf.openpdf.service.table;

import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.service.NumberingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class NumberingServiceTest {

    @InjectMocks
    private NumberingService numberingService;

    @Test
    void givenNumberedTextBlocks_whenNumberingBlocks_thenNumberingIsCorrect() {
        TemplateTextDto block1 = new TemplateTextDto();
        block1.setNumbering(true);
        block1.setNumberingLevel(1);
        TemplateTextDto block2 = new TemplateTextDto();
        block2.setNumbering(true);
        block2.setNumberingLevel(1);
        TemplateTextDto block3 = new TemplateTextDto();
        block3.setNumbering(true);
        block3.setNumberingLevel(2);
        TemplateTextDto block4 = new TemplateTextDto();
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
