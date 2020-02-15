package generate.pdf.openpdf.service.table;

import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.service.DynamicDataInjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCellService {

    private final DynamicDataInjectionService dynamicDataInjectionService;

    public PdfPCell createCellWithStyles(
            Font font,
            TextBlockWithStyle textBlockWithStyle
    ) {
        font.setSize(textBlockWithStyle.getTextSize());
        Phrase phrase = dynamicDataInjectionService.getBoldStrings(font, textBlockWithStyle.getTextBlockValue());
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(textBlockWithStyle.getVerticalAlignment());
        cell.setHorizontalAlignment(textBlockWithStyle.getHorizontalAlignment());
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public PdfPCell createEmptyCellWithNoStyles() {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
