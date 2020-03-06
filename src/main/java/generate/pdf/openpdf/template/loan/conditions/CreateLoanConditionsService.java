package generate.pdf.openpdf.template.loan.conditions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.table.CreateCellService;
import generate.pdf.openpdf.template.loan.dto.LoanContractInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static generate.pdf.openpdf.dto.TemplateTextBlock.createNewBlockFromExistingWithSameStyles;

@Service
@RequiredArgsConstructor
public class CreateLoanConditionsService {

    private static final Integer NUMBERING_START = 0;

    private final CreateCellService createCellService;
    private final TextBlockService textBlockService;

    private LoanContractInputDto loanContractInputDto;
    private Map<String, TemplateTextBlock> textBlocksWithStyle;
    private Map<String, Object> inputDataAsMap;
    private String url;
    private Font font;
    private LinkedList<Integer> numberingMemory;

    public void createMainConditions(
            Document document,
            Map<String, TemplateTextBlock> textBlockMap,
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
        List<TemplateTextBlock> textsByGroup = textBlockService.getTextsByGroup(textBlocksWithStyle, "MAIN_CONDITIONS");
        numberingMemory.add(NUMBERING_START);
        for (TemplateTextBlock text : textsByGroup) {
            if (text.getTextBlockName().equals("LOAN_TRANSFER_PARAGRAPH_2")
                    && loanContractInputDto != null
                    && BigDecimal.ZERO.compareTo(loanContractInputDto.getLoan().getConclusionFee()) == 0) {
                continue;
            }
            String number = getNumberForTextBlock(text);
            createTwoCellRow(table, number, text);
        }
    }

    private String getNumberForTextBlock(TemplateTextBlock text) {
        if (!text.isNumbering() || text.getNumberingLevel() == null) {
            return "";
        }
        if (numberingMemory.size() == text.getNumberingLevel()) {
            Integer number = numberingMemory.removeLast();
            numberingMemory.addLast(number + 1);
            StringBuilder sb = new StringBuilder();
            numberingMemory.forEach(num -> sb.append(num).append("."));
            return sb.toString();
        }
        if (numberingMemory.size() > text.getNumberingLevel()) {
            numberingMemory.removeLast();
            return getNumberForTextBlock(text);
        }
        if (numberingMemory.size() < text.getNumberingLevel()) {
            numberingMemory.addLast(NUMBERING_START);
            return getNumberForTextBlock(text);
        }
        return "";
    }

    private void createTwoCellRow(PdfPTable table, String number, TemplateTextBlock textWithStyle) {
        PdfPCell cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font,
                createNewBlockFromExistingWithSameStyles(textWithStyle, number), inputDataAsMap, null);
        cell.setHorizontalAlignment(0);
        table.addCell(cell);

        cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, textWithStyle, inputDataAsMap, url);
        table.addCell(cell);
    }

}
