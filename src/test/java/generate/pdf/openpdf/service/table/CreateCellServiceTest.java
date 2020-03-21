package generate.pdf.openpdf.service.table;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.service.DynamicDataInjectionService;
import generate.pdf.openpdf.service.FontStylesCreationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCellServiceTest {

    private static Long TEMPLATE_TEXT_ID = 1L;

    @Mock
    private DynamicDataInjectionService dynamicDataInjectionService;
    @Mock
    private FontStylesCreationService fontStylesCreationService;
    @InjectMocks
    private CreateCellService createCellService;

    private TemplateTextBlock templateTextBlock;
    private Font font;
    private String fullString;
    private String replacement;

    @BeforeEach
    void initUseCase() {
        replacement = "Catherine Aird";
        fullString = "'If you can't be a good example, then you'll just have to be a horrible warning.' - Catherine Aird";

        templateTextBlock = new TemplateTextBlock();
        templateTextBlock.setHorizontalAlignment(1);
        templateTextBlock.setVerticalAlignment(5);
        templateTextBlock.setTextSize(12.0f);
        templateTextBlock.setTemplateTextId(TEMPLATE_TEXT_ID);
        templateTextBlock.setTextBlockValue("'If you can't be a good example, then you'll just have to be a horrible warning.' - ${author}");

        font = new Font(Font.HELVETICA);

        when(fontStylesCreationService.createPhraseWithInlineStyles(any(), any()))
                .thenReturn(new Phrase(fullString));
    }

    @Test
    void testCreateCellAndInsertGivenString() {
        when(dynamicDataInjectionService.injectGivenValue(any(), any())).thenReturn(replacement);

        PdfPCell cell = createCellService.createCellAndInsertGivenString(font, templateTextBlock, replacement, null);

        assertEquals(0, cell.getBorder());
        assertEquals(1, cell.getHorizontalAlignment());
        assertEquals(5, cell.getVerticalAlignment());
        StringBuilder sb = new StringBuilder();
        for (Element chunks : cell.getPhrase().getChunks()) {
            sb.append(((Chunk) chunks).getContent());
        }
        assertEquals(fullString, sb.toString());
    }

    @Test
    void testCreateCellAndInsertGivenStringWithUrl() {
        when(dynamicDataInjectionService.injectGivenValue(any(), any())).thenReturn(replacement);

        String url = "https://www.taltech.ee/";
        PdfPCell cell = createCellService.createCellAndInsertGivenString(font, templateTextBlock, replacement, url);

        assertEquals(0, cell.getBorder());
        assertEquals(1, cell.getHorizontalAlignment());
        assertEquals(5, cell.getVerticalAlignment());
        StringBuilder sb = new StringBuilder();
        for (Element chunks : cell.getPhrase().getChunks()) {
            sb.append(((Chunk) chunks).getContent());
        }
        assertEquals(fullString, sb.toString());
        assertEquals("https://www.taltech.ee/" + TEMPLATE_TEXT_ID, ((LinkInCell)cell.getCellEvent()).getUrl());
    }

    @Test
    void testCreateCellAndInsertDynamicData() {
        when(dynamicDataInjectionService.injectValues(any(), any())).thenReturn(replacement);

        Map<String, Object> map = new HashMap<>();
        PdfPCell cell = createCellService.createCellAndInsertDynamicData(font, templateTextBlock, map, null);

        assertEquals(0, cell.getBorder());
        assertEquals(1, cell.getHorizontalAlignment());
        assertEquals(5, cell.getVerticalAlignment());
        StringBuilder sb = new StringBuilder();
        for (Element chunks : cell.getPhrase().getChunks()) {
            sb.append(((Chunk) chunks).getContent());
        }
        assertEquals(fullString, sb.toString());
    }

    @Test
    void testCreateCellAndInsertDynamicDataWithUrl() {
        when(dynamicDataInjectionService.injectValues(any(), any())).thenReturn(replacement);

        Map<String, Object> map = new HashMap<>();
        String url = "https://www.taltech.ee/";
        PdfPCell cell = createCellService.createCellAndInsertDynamicData(font, templateTextBlock, map, url);

        assertEquals(0, cell.getBorder());
        assertEquals(1, cell.getHorizontalAlignment());
        assertEquals(5, cell.getVerticalAlignment());
        StringBuilder sb = new StringBuilder();
        for (Element chunks : cell.getPhrase().getChunks()) {
            sb.append(((Chunk) chunks).getContent());
        }
        assertEquals(fullString, sb.toString());
        assertEquals("https://www.taltech.ee/" + TEMPLATE_TEXT_ID, ((LinkInCell)cell.getCellEvent()).getUrl());
    }

}
