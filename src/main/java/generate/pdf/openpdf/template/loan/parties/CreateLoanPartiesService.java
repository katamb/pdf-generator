package generate.pdf.openpdf.template.loan.parties;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.service.table.CreateCellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateLoanPartiesService {

    private static final int PADDING_BOTTOM = 4;

    private final CreateCellService createCellService;

    private Map<String, TemplateTextBlock> textBlocksWithStyle;
    private Map<String, Object> inputDataAsMap;
    private String url;
    private Font font;

    public void createPartiesData(
            Document document,
            Map<String, TemplateTextBlock> textBlocksWithStyle,
            Map<String, Object> inputDataAsMap,
            String url,
            Font font
    ) {
        this.textBlocksWithStyle = textBlocksWithStyle;
        this.inputDataAsMap = inputDataAsMap;
        this.url = url;
        this.font = font;

        PdfPTable table = new PdfPTable(5);
        table.setTotalWidth(new float[]{ 90, 165, 20, 90, 165 });
        table.setLockedWidth(true);

        createFirstRow(table);

        createOtherRows(table);

        document.add(table);
    }

    private void createFirstRow(PdfPTable table) {
        TemplateTextBlock templateTextBlock = textBlocksWithStyle.get("LENDER");
        PdfPCell cell = createCellService.createCellAndInsertDynamicData(font, templateTextBlock, inputDataAsMap, url);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(Color.GRAY);
        cell.setPaddingBottom(PADDING_BOTTOM);
        cell.setPaddingTop(8);
        cell.setColspan(2);
        table.addCell(cell);

        table.addCell(createCellService.createEmptyCell());

        templateTextBlock = textBlocksWithStyle.get("BORROWER");
        cell = createCellService.createCellAndInsertDynamicData(font, templateTextBlock, inputDataAsMap, url);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(Color.GRAY);
        cell.setPaddingBottom(PADDING_BOTTOM);
        cell.setPaddingTop(8);
        cell.setColspan(2);
        table.addCell(cell);
    }

    private void createOtherRows(PdfPTable table) {
        List<String> row = Arrays.asList("NAME", "LENDER_NAME", null, "NAME", "BORROWER_NAME");
        createFiveCellRow(table, row);
        row = Arrays.asList("PHONE", "LENDER_PHONE",  null, "PHONE", "BORROWER_PHONE");
        createFiveCellRow(table, row);
        row = Arrays.asList("ADDRESS", "LENDER_ADDRESS",  null, "ADDRESS", "BORROWER_ADDRESS");
        createFiveCellRow(table, row);
    }

    private void createFiveCellRow(PdfPTable table, List<String> cellNames) {
        for (String cellName : cellNames) {
            if (cellName == null) {
                table.addCell(createCellService.createEmptyCell());
                continue;
            }
            TemplateTextBlock templateTextBlock = textBlocksWithStyle.get(cellName);
            PdfPCell cell = createCellService.createCellAndInsertDynamicData(font, templateTextBlock, inputDataAsMap, url);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(Color.GRAY);
            cell.setPaddingBottom(PADDING_BOTTOM);
            table.addCell(cell);
        }
    }

}
