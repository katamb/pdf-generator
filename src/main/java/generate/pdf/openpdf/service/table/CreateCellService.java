package generate.pdf.openpdf.service.table;

import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.service.DynamicDataInjectionService;
import generate.pdf.openpdf.template.loan.schedule.LinkInCell;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateCellService {

    private final DynamicDataInjectionService dynamicDataInjectionService;

    public PdfPCell createCellWithStylesWhenDynamicDataGiven(
            Font font,
            TextBlockWithStyle textBlockWithStyle,
            String replacement
    ) {
        font.setSize(textBlockWithStyle.getTextSize());
        String injectedText = getText(textBlockWithStyle, replacement);
        Phrase phrase = dynamicDataInjectionService.getBoldStrings(font, injectedText);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(textBlockWithStyle.getVerticalAlignment());
        cell.setHorizontalAlignment(textBlockWithStyle.getHorizontalAlignment());
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public PdfPCell createCellWithStylesDynamicDataFromMapIfPossible(
            Font font,
            TextBlockWithStyle textBlockWithStyle,
            Map<String, Object> inputData,
            String url
    ) {
        font.setSize(textBlockWithStyle.getTextSize());
        String injectedText = getText(textBlockWithStyle, inputData);
        Phrase phrase = dynamicDataInjectionService.getBoldStrings(font, injectedText);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(textBlockWithStyle.getVerticalAlignment());
        cell.setHorizontalAlignment(textBlockWithStyle.getHorizontalAlignment());
        cell.setBorder(Rectangle.NO_BORDER);
        if (url != null) {
            cell.setCellEvent(new LinkInCell(url + textBlockWithStyle.getTemplateTextId()));
        }
        return cell;
    }

    public PdfPCell createCellWithStylesNoSubstitutions(Font font, TextBlockWithStyle textBlockWithStyle, String url) {
        font.setSize(textBlockWithStyle.getTextSize());
        Phrase phrase = dynamicDataInjectionService.getBoldStrings(font, textBlockWithStyle.getTextBlockValue());
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(textBlockWithStyle.getVerticalAlignment());
        cell.setHorizontalAlignment(textBlockWithStyle.getHorizontalAlignment());
        cell.setBorder(Rectangle.NO_BORDER);
        if (url != null) {
            cell.setCellEvent(new LinkInCell(url + textBlockWithStyle.getTemplateTextId()));
        }
        return cell;
    }

    private String getText(TextBlockWithStyle textBlockWithStyle, Map<String, Object> inputData) {
        if (inputData == null) {
            return textBlockWithStyle.getTextBlockValue();
        } else {
            return dynamicDataInjectionService.injectValues(textBlockWithStyle.getTextBlockValue(), inputData);
        }
    }

    private String getText(TextBlockWithStyle textBlockWithStyle, String inputData) {
        if (inputData == null) {
            return textBlockWithStyle.getTextBlockValue();
        } else {
            return dynamicDataInjectionService.injectValue(textBlockWithStyle.getTextBlockValue(), inputData);
        }
    }

    public PdfPCell createEmptyCellWithNoStyles() {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
