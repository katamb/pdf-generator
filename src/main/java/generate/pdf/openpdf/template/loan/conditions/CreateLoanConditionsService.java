package generate.pdf.openpdf.template.loan.conditions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.service.NumberingService;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.table.CreateCellService;
import generate.pdf.openpdf.template.loan.dto.LoanContractInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static generate.pdf.openpdf.dto.TemplateTextDto.createNewBlockFromExistingWithSameStyles;

@Service
@RequiredArgsConstructor
public class CreateLoanConditionsService {

    private final CreateCellService createCellService;
    private final TextBlockService textBlockService;
    private final NumberingService numberingService;

    private LoanContractInputDto loanContractInputDto;
    private Map<String, TemplateTextDto> textBlocksWithStyle;
    private Map<String, Object> inputDataAsMap;
    private String url;
    private Font font;
    private LinkedList<Integer> numberingMemory;

    public void createMainConditions(
            Document document,
            Map<String, TemplateTextDto> textBlockMap,
            LoanContractInputDto loanContractInputDto,
            Map<String, Object> inputDataAsMap,
            String url,
            Font font
    ) {
        this.loanContractInputDto = loanContractInputDto;
        this.textBlocksWithStyle = textBlockMap;
        this.inputDataAsMap = inputDataAsMap;
        this.font = font;
        this.url = url;
        this.numberingMemory = new LinkedList<>();

        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(new float[]{ 40, 450 });
        table.setLockedWidth(true);

        createConditionRows(table);

        document.add(table);
    }

    private void createConditionRows(PdfPTable table) {
        List<TemplateTextDto> textsByGroup = textBlockService.getTextsByGroup(textBlocksWithStyle, "MAIN_CONDITIONS");
        for (TemplateTextDto text : textsByGroup) {
            if (text.getTextBlockName().equals("LOAN_TRANSFER_PARAGRAPH_2")
                    && loanContractInputDto != null
                    && BigDecimal.ZERO.compareTo(loanContractInputDto.getLoan().getConclusionFee()) == 0) {
                continue;
            }
            String number = numberingService.getNumberForTextBlock(text, numberingMemory);
            createTwoCellRow(table, number, text);
        }
    }

    private void createTwoCellRow(PdfPTable table, String number, TemplateTextDto textWithStyle) {
        PdfPCell cell = createCellService.createCellAndInsertDynamicData(font,
                createNewBlockFromExistingWithSameStyles(textWithStyle, number), inputDataAsMap, null);
        cell.setHorizontalAlignment(0);
        table.addCell(cell);

        cell = createCellService.createCellAndInsertDynamicData(font, textWithStyle, inputDataAsMap, url);
        table.addCell(cell);
    }

}
