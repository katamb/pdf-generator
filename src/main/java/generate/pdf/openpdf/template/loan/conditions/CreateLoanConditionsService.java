package generate.pdf.openpdf.template.loan.conditions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
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

    private LoanContractInputDto loanContractInputDto;
    private Map<String, TextBlockWithStyle> textBlocksWithStyle;
    private Map<String, Object> inputDataAsMap;
    private String url;
    private Font font;

    public void createMainConditions(
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

        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(new float[]{ 40, 450 });
        table.setLockedWidth(true);

        createConditionRows(table);

        document.add(table);
    }

    private void createConditionRows(PdfPTable table) {
        createTwoCellRow(table, "", textBlocksWithStyle.get("LOAN_TRANSFER_PARAGRAPH_HEADING"));
        createTwoCellRow(table, "1.", textBlocksWithStyle.get("LOAN_TRANSFER_PARAGRAPH_1"));
        createTwoCellRow(table, "2.", textBlocksWithStyle.get("LOAN_TRANSFER_PARAGRAPH_2"));
        createTwoCellRow(table, "", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_HEADING"));
        createTwoCellRow(table, "3.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_1"));
        createTwoCellRow(table, "4.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_2"));
        createTwoCellRow(table, "5.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_3"));
        createTwoCellRow(table, "6.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4"));
        createTwoCellRow(table, "6.1.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4_1"));
        createTwoCellRow(table, "6.2.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4_2"));
        createTwoCellRow(table, "6.3.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4_3"));
        createTwoCellRow(table, "6.4.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_4_4"));
        createTwoCellRow(table, "7.", textBlocksWithStyle.get("INTEREST_AND_LOAN_PARAGRAPH_5"));
        createTwoCellRow(table, "", textBlocksWithStyle.get("PENALTY_PARAGRAPH_HEADING"));
        createTwoCellRow(table, "8.", textBlocksWithStyle.get("PENALTY_PARAGRAPH_1"));
        createTwoCellRow(table, "", textBlocksWithStyle.get("LENDER_RIGHTS_HEADING"));
        createTwoCellRow(table, "9.", textBlocksWithStyle.get("LENDER_RIGHTS_PARAGRAPH_1"));
        createTwoCellRow(table, "10.", textBlocksWithStyle.get("LENDER_RIGHTS_PARAGRAPH_1_1"));
        createTwoCellRow(table, "11.", textBlocksWithStyle.get("LENDER_RIGHTS_PARAGRAPH_1_2"));
        createTwoCellRow(table, "", textBlocksWithStyle.get("COLLATERAL_PARAGRAPH_HEADING"));
        createTwoCellRow(table, "12.", textBlocksWithStyle.get("COLLATERAL_PARAGRAPH_1"));
        createTwoCellRow(table, "", textBlocksWithStyle.get("ARGUMENT_PARAGRAPH_HEADING"));
        createTwoCellRow(table, "12.", textBlocksWithStyle.get("ARGUMENT_PARAGRAPH_1"));
        createTwoCellRow(table, "", textBlocksWithStyle.get("CONTRACT_ENACTMENT_HEADING"));
        createTwoCellRow(table, "13.", textBlocksWithStyle.get("CONTRACT_ENACTMENT_1"));
        createTwoCellRow(table, "", textBlocksWithStyle.get("CONTRACT_INFO_HEADING"));
        createTwoCellRow(table, "14.", textBlocksWithStyle.get("CONTRACT_INFO_1"));
    }

    private void createTwoCellRow(PdfPTable table, String number, TextBlockWithStyle textWithStyle) {
        PdfPCell cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font,
                createNewBlockFromExistingWithSameStyles(textWithStyle, number), inputDataAsMap, null);
        cell.setHorizontalAlignment(0);
        table.addCell(cell);

        cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, textWithStyle, inputDataAsMap, url);
        table.addCell(cell);
    }

}
