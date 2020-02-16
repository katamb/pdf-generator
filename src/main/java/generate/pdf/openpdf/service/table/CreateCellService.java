package generate.pdf.openpdf.service.table;

import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.service.DynamicDataInjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateCellService {

    private final DynamicDataInjectionService dynamicDataInjectionService;

    public PdfPCell createCellWithStyles(
            Font font,
            TextBlockWithStyle textBlockWithStyle,
            Map<String, Object> map
    ) {
        font.setSize(textBlockWithStyle.getTextSize());
        String injectedText = getText(textBlockWithStyle, map);
        Phrase phrase = dynamicDataInjectionService.getBoldStrings(font, injectedText);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(textBlockWithStyle.getVerticalAlignment());
        cell.setHorizontalAlignment(textBlockWithStyle.getHorizontalAlignment());
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private String getText(TextBlockWithStyle textBlockWithStyle, Map<String, Object> map) {
        if (map == null) {
            return textBlockWithStyle.getTextBlockValue();
        } else {
            return dynamicDataInjectionService.injectValues(textBlockWithStyle.getTextBlockValue(), map);
        }
    }

    public PdfPCell createEmptyCellWithNoStyles() {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
