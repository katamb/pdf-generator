package generate.pdf.openpdf.service.table;

import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import generate.pdf.openpdf.dto.TemplateTextDto;
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
            TemplateTextDto templateTextDto,
            String replacement,
            String url
    ) {
        String finalText = getText(templateTextDto, replacement);
        return getPdfPCell(font, templateTextDto, url, finalText);
    }

    public PdfPCell createCellAndInsertDynamicData(
            Font font,
            TemplateTextDto templateTextDto,
            Map<String, Object> replacementValues,
            String url
    ) {
        String finalText = getText(templateTextDto, replacementValues);
        return getPdfPCell(font, templateTextDto, url, finalText);
    }

    public PdfPCell createCellMakeNoSubstitutions(Font font, TemplateTextDto templateTextDto, String url) {
        String finalText = templateTextDto.getTextBlockValue();
        return getPdfPCell(font, templateTextDto, url, finalText);
    }

    private PdfPCell getPdfPCell(Font font, TemplateTextDto templateTextDto, String url, String finalText) {
        font.setSize(templateTextDto.getTextSize());
        Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, finalText);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(templateTextDto.getPaddingTop());
        cell.setPaddingBottom(templateTextDto.getPaddingBottom());
        cell.setPaddingLeft(templateTextDto.getPaddingLeft());
        cell.setPaddingRight(templateTextDto.getPaddingRight());
        cell.setVerticalAlignment(templateTextDto.getVerticalAlignment());
        cell.setHorizontalAlignment(templateTextDto.getHorizontalAlignment());
        if (url != null) {
            cell.setCellEvent(new LinkInCell(url + templateTextDto.getTemplateTextId()));
        }
        return cell;
    }

    private String getText(TemplateTextDto templateTextDto, Map<String, Object> inputData) {
        String text = templateTextDto.getTextBlockValue();
        return inputData == null ? text : dynamicDataInjectionService.injectValues(text, inputData);
    }

    private String getText(TemplateTextDto templateTextDto, String inputData) {
        String text = templateTextDto.getTextBlockValue();
        return inputData == null ? text : dynamicDataInjectionService.injectGivenValue(text, inputData);
    }

    public PdfPCell createEmptyCell() {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

}
