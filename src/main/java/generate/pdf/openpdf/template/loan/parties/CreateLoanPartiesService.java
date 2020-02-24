package generate.pdf.openpdf.template.loan.parties;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.template.loan.dto.LoanContractInputDto;
import generate.pdf.openpdf.service.table.CreateCellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateLoanPartiesService {

    private final CreateCellService createCellService;

    private LoanContractInputDto loanContractInputDto;
    private Map<String, TextBlockWithStyle> textBlocksWithStyle;
    private Map<String, Object> inputDataAsMap;
    private String url;
    private Font font;

    public void createPartiesData(
            Document document,
            Map<String, TextBlockWithStyle> textBlocksWithStyle,
            LoanContractInputDto loanContractInputDto,
            Map<String, Object> inputDataAsMap,
            String url
    ) {
        this.loanContractInputDto = loanContractInputDto;
        this.textBlocksWithStyle = textBlocksWithStyle;
        this.inputDataAsMap = inputDataAsMap;
        this.font = new Font(Font.HELVETICA);
        this.url = url;

        PdfPTable table = new PdfPTable(5);
        table.setTotalWidth(new float[]{ 90, 165, 20, 90, 165 });
        table.setLockedWidth(true);

        createFirstRow(table);

        createOtherRows(table);

        document.add(table);
    }

    private void createFirstRow(PdfPTable table) {
        TextBlockWithStyle textBlockWithStyle = textBlocksWithStyle.get("LENDER");
        PdfPCell cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, textBlockWithStyle, inputDataAsMap, url);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(2);
        table.addCell(cell);

        table.addCell(createCellService.createEmptyCellWithNoStyles());

        textBlockWithStyle = textBlocksWithStyle.get("BORROWER");
        cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, textBlockWithStyle, inputDataAsMap, url);
        cell.setBorder(Rectangle.BOTTOM);
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
                table.addCell(createCellService.createEmptyCellWithNoStyles());
                continue;
            }
            TextBlockWithStyle textBlockWithStyle = textBlocksWithStyle.get(cellName);
            PdfPCell cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, textBlockWithStyle, inputDataAsMap, url);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
        }
    }

}
