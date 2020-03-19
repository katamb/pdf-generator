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
        font.setSize(templateTextBlock.getTextSize());
        String injectedText = getText(templateTextBlock, replacement);
        Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, injectedText);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(templateTextBlock.getVerticalAlignment());
        cell.setHorizontalAlignment(templateTextBlock.getHorizontalAlignment());
        cell.setBorder(Rectangle.NO_BORDER);
        if (url != null) {
            cell.setCellEvent(new LinkInCell(url + templateTextBlock.getTemplateTextId()));
        }
        return cell;
    }

    public PdfPCell createCellAndInsertDynamicDataIfPossible(
            Font font,
            TemplateTextBlock templateTextBlock,
            Map<String, Object> inputData,
            String url
    ) {
        font.setSize(templateTextBlock.getTextSize());
        String injectedText = getText(templateTextBlock, inputData);
        Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, injectedText);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(templateTextBlock.getVerticalAlignment());
        cell.setHorizontalAlignment(templateTextBlock.getHorizontalAlignment());
        cell.setBorder(Rectangle.NO_BORDER);
        if (url != null) {
            cell.setCellEvent(new LinkInCell(url + templateTextBlock.getTemplateTextId()));
        }
        return cell;
    }

    public PdfPCell createCellMakeNoSubstitutions(Font font, TemplateTextBlock templateTextBlock, String url) {
        font.setSize(templateTextBlock.getTextSize());
        Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, templateTextBlock.getTextBlockValue());
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
        if (inputData == null) {
            return templateTextBlock.getTextBlockValue();
        } else {
            return dynamicDataInjectionService.injectValues(templateTextBlock.getTextBlockValue(), inputData);
        }
    }

    private String getText(TemplateTextBlock templateTextBlock, String inputData) {
        if (inputData == null) {
            return templateTextBlock.getTextBlockValue();
        } else {
            return dynamicDataInjectionService.injectValue(templateTextBlock.getTextBlockValue(), inputData);
        }
    }

    public PdfPCell createEmptyCellWithNoStyles() {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
