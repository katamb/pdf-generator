package generate.pdf.openpdf.template.editable.form;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.table.CreateCellService;
import generate.pdf.openpdf.service.table.RadioCellField;
import generate.pdf.openpdf.service.table.TextInputCellField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateFormFieldsService {

    private static final int SPACER_ROW_HEIGHT = 8;
    private static final int EMPTY_ROW_HEIGHT = 5;
    private static final int COLUMNS_AMOUNT = 3;

    private final CreateCellService createCellService;
    private final TextBlockService textBlockService;

    private Map<String, TemplateTextBlock> textBlocksWithStyle;
    private Map<String, Object> inputDataAsMap;
    private PdfWriter writer;
    private String url;
    private Font font;
    private List<PdfFormField> pdfFormFields;

    public void createForm(
            PdfWriter writer,
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
        this.writer = writer;
        this.pdfFormFields = new ArrayList<>();

        PdfPTable table = new PdfPTable(COLUMNS_AMOUNT);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        createIntroduction(table);
        createFormGroups(table);

        document.add(table);

        // For radio button, this @writer.addAnnotation() method needs to be called after @document.add()
        for (PdfFormField formField : pdfFormFields) {
            writer.addAnnotation(formField);
        }
    }

    private void createIntroduction(PdfPTable table) {
        TemplateTextBlock text = textBlocksWithStyle.get("PDF_FORM");
        PdfPCell cell = createCellService.createCellAndInsertDynamicData(font, text, inputDataAsMap, url);
        cell.setColspan(COLUMNS_AMOUNT);
        table.addCell(cell);
        text = textBlocksWithStyle.get("INTRODUCTION");
        cell = createCellService.createCellAndInsertDynamicData(font, text, inputDataAsMap, url);
        cell.setColspan(COLUMNS_AMOUNT);
        table.addCell(cell);
    }

    private void createFormGroups(PdfPTable table) {
        List<String> formBlockGroups = Arrays.asList("GENERAL_DATA", "LOCATION_DATA", "OTHER_DATA");
        for (String textBlockGroup : formBlockGroups) {
            List<TemplateTextBlock> textsByGroup = textBlockService.getTextsByGroup(textBlocksWithStyle, textBlockGroup);
            createFormRows(table, textsByGroup);
            createSpacer(table, SPACER_ROW_HEIGHT);
        }
    }

    private void createFormRows(PdfPTable table, List<TemplateTextBlock> textsByGroup) {
        for (TemplateTextBlock textByGroup : textsByGroup) {
            // Create heading rows
            if (textByGroup.getTextBlockName().contains("HEADING")) {
                PdfPCell cell = createCellService.createCellAndInsertDynamicData(font, textByGroup, inputDataAsMap, url);
                cell.setColspan(COLUMNS_AMOUNT);
                table.addCell(cell);
                continue;
            }

            PdfPCell cell = createCellService.createCellAndInsertDynamicData(font, textByGroup, inputDataAsMap, url);
            table.addCell(cell);

            if (textByGroup.getTextBlockName().equals("GENERAL_DATA_3")) {
                createGenderRadioButtonRow(table);
            } else {
                cell = createTextFieldCell(textByGroup);
                table.addCell(cell);
            }

            createSpacer(table, EMPTY_ROW_HEIGHT);
        }
    }

    private void createGenderRadioButtonRow(PdfPTable table) {
        PdfFormField radioGroup = PdfFormField.createRadioButton(writer, true);
        pdfFormFields.add(radioGroup);
        radioGroup.setFieldName("Gender");
        PdfPTable innerTable = new PdfPTable(6);
        innerTable.setTotalWidth(new float[]{90, 20, 90, 20, 90, 20});
        List<TemplateTextBlock> textsByGroup = textBlockService.getTextsByGroup(textBlocksWithStyle, "GENDER_CHOICE");

        for (TemplateTextBlock textByGroup : textsByGroup) {
            PdfPCell cell = createCellService.createCellAndInsertDynamicData(font, textByGroup, inputDataAsMap, url);
            cell.setBorder(Rectangle.NO_BORDER);
            innerTable.addCell(cell);
            cell = new PdfPCell();
            String fieldName = textByGroup.getTextBlockValue().toLowerCase().replace(":", "");
            cell.setCellEvent(new RadioCellField(radioGroup, fieldName));
            innerTable.addCell(cell);
        }
        PdfPCell innerTableCell = new PdfPCell();
        innerTableCell.setColspan(2);
        innerTableCell.addElement(innerTable);
        innerTableCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(innerTableCell);
    }

    private PdfPCell createTextFieldCell(TemplateTextBlock textByGroup) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(COLUMNS_AMOUNT - 1);
        cell.setBorder(Rectangle.BOTTOM);
        String fieldName = textByGroup.getTextBlockValue().toLowerCase().replace(":", "");
        cell.setCellEvent(new TextInputCellField(fieldName, textByGroup.getTextSize(), false));
        return cell;
    }

    private void createSpacer(PdfPTable table, int height) {
        PdfPCell cell = createCellService.createEmptyCell();
        cell.setMinimumHeight(height);
        cell.setColspan(COLUMNS_AMOUNT);
        table.addCell(cell);
    }

}
