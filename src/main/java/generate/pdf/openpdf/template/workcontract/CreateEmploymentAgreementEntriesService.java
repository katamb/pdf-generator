package generate.pdf.openpdf.template.workcontract;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfSignature;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.service.NumberingService;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.table.CreateCellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static generate.pdf.openpdf.dto.TemplateTextBlock.createNewBlockFromExistingWithSameStyles;

@Service
@RequiredArgsConstructor
public class CreateEmploymentAgreementEntriesService {

    private final CreateCellService createCellService;
    private final TextBlockService textBlockService;
    private final NumberingService numberingService;

    private Map<String, TemplateTextBlock> textBlocksWithStyle;
    private Map<String, Object> inputDataAsMap;
    private String url;
    private Font font;
    private LinkedList<Integer> numberingMemory;

    public void createEntries(
            Document document,
            Map<String, TemplateTextBlock> textBlockMap,
            Map<String, Object> inputDataAsMap,
            String url,
            Font font
    ) {
        this.textBlocksWithStyle = textBlockMap;
        this.inputDataAsMap = inputDataAsMap;
        this.font = font;
        this.url = url;
        this.numberingMemory = new LinkedList<>();

        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(new float[]{ 40, 450 });
        table.setLockedWidth(true);

        createHeaderCell(document);
        createConditionRows(table);

        document.add(table);
    }

    public void createHeaderCell(Document document) {
        PdfPTable table = new PdfPTable(1);

        table.addCell(createCellService.createCellAndInsertDynamicData(font, textBlocksWithStyle.get("EMPLOYMENT_AGREEMENT"), inputDataAsMap, url));

        document.add(table);
    }

    private void createConditionRows(PdfPTable table) {
        List<String> groups = Arrays.asList("GENERAL", "MAIN_CONDITIONS", "END_CONDITIONS");
        for (String group : groups) {
            List<TemplateTextBlock> textsByGroup = textBlockService.getTextsByGroup(textBlocksWithStyle, group);
            for (TemplateTextBlock text : textsByGroup) {
                String number = numberingService.getNumberForTextBlock(text, numberingMemory);
                createTwoCellRow(table, number, text);
            }
        }
        for (TemplateTextBlock text : textBlockService.getTextsByGroup(textBlocksWithStyle, "CONDITION_WITH_INPUT")) {
            String number = numberingService.getNumberForTextBlock(text, numberingMemory);
            createTwoCellRowWithInput(table, number, text);
        }
    }

    private void createTwoCellRow(PdfPTable table, String number, TemplateTextBlock textWithStyle) {
        PdfPCell cell = createCellService.createCellAndInsertDynamicData(font,
                createNewBlockFromExistingWithSameStyles(textWithStyle, number), inputDataAsMap, null);
        cell.setHorizontalAlignment(0);
        table.addCell(cell);

        cell = createCellService.createCellAndInsertDynamicData(font, textWithStyle, inputDataAsMap, url);
        table.addCell(cell);
    }

    private void createTwoCellRowWithInput(PdfPTable table, String number, TemplateTextBlock textWithStyle) {
        PdfPCell cell = createCellService.createEmptyCell();
        cell.setBorder(Rectangle.BOTTOM);
        cell.setFixedHeight(15);

        table.addCell(cell);
        table.addCell(cell);

        createTwoCellRow(table, number, textWithStyle);
    }

}
