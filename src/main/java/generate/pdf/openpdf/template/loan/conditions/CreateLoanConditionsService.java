package generate.pdf.openpdf.template.loan.conditions;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.service.table.CreateCellService;
import generate.pdf.openpdf.template.loan.dto.LoanContractInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

import static generate.pdf.openpdf.dto.TextBlockWithStyle.createNewBlockFromExistingWithSameStyles;

@Service
@RequiredArgsConstructor
public class CreateLoanConditionsService {

    private final CreateCellService createCellService;

    public void createMainConditions(
            Document document,
            Map<String, TextBlockWithStyle> textBlocksWithStyle,
            LoanContractInputDto loanContractInputDto,
            Map<String, Object> map,
            String url
    ) {
        Font font = new Font(Font.HELVETICA);

        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(new float[]{ 40, 450 });
        table.setLockedWidth(true);

        createConditionRows(textBlocksWithStyle, table, font, loanContractInputDto, map, url);

        document.add(table);
    }

    private void createConditionRows(
            Map<String, TextBlockWithStyle> textBlocksWithStyle,
            PdfPTable table,
            Font font,
            LoanContractInputDto loanContractInputDto,
            Map<String, Object> map,
            String url
    ) {
        createTwoCellRow(table, font, "", textBlocksWithStyle.get("LOAN_TRANSFER_PARAGRAPH_HEADING"), map, url);
        createTwoCellRow(table, font, "1.", textBlocksWithStyle.get("LOAN_TRANSFER_PARAGRAPH_1"), map, url);
        createTwoCellRow(table, font, "2.", textBlocksWithStyle.get("LOAN_TRANSFER_PARAGRAPH_2"), map, url);
        createTwoCellRow(table, font, "", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_HEADING"), map, url);
        createTwoCellRow(table, font, "3.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_1"), map, url);
        createTwoCellRow(table, font, "4.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_2"), map, url);
        createTwoCellRow(table, font, "5.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_3"), map, url);
        createTwoCellRow(table, font, "6.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4"), map, url);
        createTwoCellRow(table, font, "6.1.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4_1"), map, url);
        createTwoCellRow(table, font, "6.2.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4_2"), map, url);
        createTwoCellRow(table, font, "6.3.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4_3"), map, url);
        createTwoCellRow(table, font, "6.4.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4_4"), map, url);
        createTwoCellRow(table, font, "7.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_5"), map, url);
        createTwoCellRow(table, font, "", textBlocksWithStyle.get("PENALTY_PARAGRAPH_HEADING"), map, url);
        createTwoCellRow(table, font, "8.", textBlocksWithStyle.get("PENALTY_PARAGRAPH_1"), map, url);
        createTwoCellRow(table, font, "", textBlocksWithStyle.get("LENDER_RIGHTS_HEADING"), map, url);
        createTwoCellRow(table, font, "9.", textBlocksWithStyle.get("LENDER_RIGHTS_PARAGRAPH_1"), map, url);
        createTwoCellRow(table, font, "10.", textBlocksWithStyle.get("LENDER_RIGHTS_PARAGRAPH_1_1"), map, url);
        createTwoCellRow(table, font, "11.", textBlocksWithStyle.get("LENDER_RIGHTS_PARAGRAPH_1_2"), map, url);
        createTwoCellRow(table, font, "", textBlocksWithStyle.get("COLLATERAL_PARAGRAPH_HEADING"), map, url);
        createTwoCellRow(table, font, "12.", textBlocksWithStyle.get("COLLATERAL_PARAGRAPH_1"), map, url);
        createTwoCellRow(table, font, "", textBlocksWithStyle.get("ARGUMENT_PARAGRAPH_HEADING"), map, url);
        createTwoCellRow(table, font, "12.", textBlocksWithStyle.get("ARGUMENT_PARAGRAPH_1"), map, url);
        createTwoCellRow(table, font, "", textBlocksWithStyle.get("CONTRACT_ENACTMENT_HEADING"), map, url);
        createTwoCellRow(table, font, "13.", textBlocksWithStyle.get("CONTRACT_ENACTMENT_1"), map, url);
        createTwoCellRow(table, font, "", textBlocksWithStyle.get("CONTRACT_INFO_HEADING"), map, url);
        createTwoCellRow(table, font, "14.", textBlocksWithStyle.get("CONTRACT_INFO_1"), map, url);
    }

    private void createTwoCellRow(
            PdfPTable table,
            Font font,
            String number,
            TextBlockWithStyle textWithStyle,
            Map<String, Object> map,
            String url
    ) {
        PdfPCell cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font,
                createNewBlockFromExistingWithSameStyles(textWithStyle, number), map, null);
        table.addCell(cell);

        cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, textWithStyle, map, url);
        table.addCell(cell);
    }

}
