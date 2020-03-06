package generate.pdf.openpdf.template.loan.text;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.service.table.CreateCellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateSimpleTextService {

    private final CreateCellService createCellService;

    private TemplateTextBlock templateTextBlock;
    private Map<String, Object> inputDataAsMap;
    private String url;
    private Font font;

    public void createSingleCell(
            Document document,
            TemplateTextBlock templateTextBlock,
            Map<String, Object> inputDataAsMap,
            String url,
            Font font
    ) {
        this.templateTextBlock = templateTextBlock;
        this.inputDataAsMap = inputDataAsMap;
        this.font = font;
        this.url = url;

        PdfPTable table = new PdfPTable(1);

        createCell(table);

        document.add(table);
    }

    private void createCell(PdfPTable table) {
        PdfPCell cell = createCellService
                .createCellWithStylesDynamicDataFromMapIfPossible(font, templateTextBlock, inputDataAsMap, url);
        cell.setPaddingTop(6);
        cell.setPaddingBottom(6);
        table.addCell(cell);
    }

}
