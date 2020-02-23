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

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateLoanPartiesService {

    private final CreateCellService createCellService;

    public void createPartiesData(Document document,
                                  Map<String, TextBlockWithStyle> textBlocksWithStyle,
                                  LoanContractInputDto loanContractInputDto,
                                  Map<String, Object> map) {
        Font font = new Font(Font.HELVETICA);

        PdfPTable table = new PdfPTable(5);
        table.setTotalWidth(new float[]{ 90, 165, 20, 90, 165 });
        table.setLockedWidth(true);

        createFirstRow(textBlocksWithStyle, table, map, font);

        createOtherRows(textBlocksWithStyle, table, font, map, loanContractInputDto);

        document.add(table);
    }

    private void createFirstRow(Map<String, TextBlockWithStyle> textBlocksWithStyle,
                                PdfPTable table,
                                Map<String, Object> map,
                                Font font) {
        TextBlockWithStyle textBlockWithStyle = textBlocksWithStyle.get("LENDER");
        PdfPCell cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, textBlockWithStyle, map);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(2);
        table.addCell(cell);

        table.addCell(createCellService.createEmptyCellWithNoStyles());

        textBlockWithStyle = textBlocksWithStyle.get("BORROWER");
        cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, textBlockWithStyle, map);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(2);
        table.addCell(cell);
    }

    private void createOtherRows(
            Map<String, TextBlockWithStyle> textBlocksWithStyle,
            PdfPTable table,
            Font font,
            Map<String, Object> map,
            LoanContractInputDto loanContractInputDto
    ) {
        createFiveCellRow(table, font, map,
                textBlocksWithStyle.get("NAME"), textBlocksWithStyle.get("LENDER_NAME"),
                textBlocksWithStyle.get("NAME"), textBlocksWithStyle.get("BORROWER_NAME"));

        createFiveCellRow(table, font, map,
                textBlocksWithStyle.get("PHONE"), textBlocksWithStyle.get("LENDER_PHONE"),
                textBlocksWithStyle.get("PHONE"), textBlocksWithStyle.get("BORROWER_PHONE"));

        createFiveCellRow(table, font, map,
                textBlocksWithStyle.get("ADDRESS"), textBlocksWithStyle.get("LENDER_ADDRESS"),
                textBlocksWithStyle.get("ADDRESS"), textBlocksWithStyle.get("BORROWER_ADDRESS"));
    }

    private void createFiveCellRow(
            PdfPTable table,
            Font font,
            Map<String, Object> map,
            TextBlockWithStyle firstCellStaticText,
            TextBlockWithStyle secondCellStaticText,
            TextBlockWithStyle thirdCellStaticText,
            TextBlockWithStyle fourthCellStaticText
    ) {
        PdfPCell cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, firstCellStaticText, map);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);

        cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, secondCellStaticText, map);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);

        table.addCell(createCellService.createEmptyCellWithNoStyles());

        cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, thirdCellStaticText, map);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);

        cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, fourthCellStaticText, map);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
    }

}
