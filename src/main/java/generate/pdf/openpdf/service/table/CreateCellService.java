package generate.pdf.openpdf.service.table;

import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.service.DynamicDataInjectionService;
import generate.pdf.openpdf.service.FontStylesCreationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateCellService {

    private static final Logger logger = LoggerFactory.getLogger(CreateCellService.class);

    private final DynamicDataInjectionService dynamicDataInjectionService;
    private final FontStylesCreationService fontStylesCreationService;

    public PdfPCell createCellAndInsertGivenString(
            Font font,
            TemplateTextBlock templateTextBlock,
            String replacement,
            String url
    ) {
        String finalText = getText(templateTextBlock, replacement);
        return getPdfPCell(font, templateTextBlock, url, finalText);
    }

    public PdfPCell createCellAndInsertDynamicData(
            Font font,
            TemplateTextBlock templateTextBlock,
            Map<String, Object> replacementValues,
            String url
    ) {
        String finalText = getText(templateTextBlock, replacementValues);
        return getPdfPCell(font, templateTextBlock, url, finalText);
    }

    public PdfPCell createCellMakeNoSubstitutions(Font font, TemplateTextBlock templateTextBlock, String url) {
        return getPdfPCell(font, templateTextBlock, url, templateTextBlock.getTextBlockValue());
    }

    private PdfPCell getPdfPCell(Font font, TemplateTextBlock templateTextBlock, String url, String finalText) {
        font.setSize(templateTextBlock.getTextSize());
        Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, finalText);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(templateTextBlock.getVerticalAlignment());
        cell.setHorizontalAlignment(templateTextBlock.getHorizontalAlignment());
        cell.setBorder(Rectangle.NO_BORDER);
        if (url != null) {
            cell.setCellEvent(new LinkInCell(url + templateTextBlock.getTemplateTextId()));
        }
        return cell;
    }

    private String getText(TemplateTextBlock templateTextBlock, Map<String, Object> inputData) {
        String text = templateTextBlock.getTextBlockValue();
        return inputData == null ? text : dynamicDataInjectionService.injectValues(text, inputData);
    }

    private String getText(TemplateTextBlock templateTextBlock, String inputData) {
        String text = templateTextBlock.getTextBlockValue();
        return inputData == null ? text : dynamicDataInjectionService.injectGivenValue(text, inputData);
    }

    public PdfPCell createEmptyCell() {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
