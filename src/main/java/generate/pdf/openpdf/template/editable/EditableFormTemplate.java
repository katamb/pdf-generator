package generate.pdf.openpdf.template.editable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.*;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.printout.BasePdfGenerator;
import generate.pdf.openpdf.service.table.CreateCellService;
import generate.pdf.openpdf.service.table.RadioCellField;
import generate.pdf.openpdf.service.table.TextInputCellField;
import generate.pdf.openpdf.template.loan.conditions.CreateLoanConditionsService;
import generate.pdf.openpdf.template.loan.parties.CreateLoanPartiesService;
import generate.pdf.openpdf.template.loan.schedule.CreateLoanScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static generate.pdf.openpdf.enums.TemplateCode.EDITABLE_FORM_EE;

@Service
@RequiredArgsConstructor
public class EditableFormTemplate extends BasePdfGenerator {

    private static final Logger logger = Logger.getLogger(String.valueOf(EditableFormTemplate.class));
    private static final List<TemplateCode> SUPPORTED_PRINTOUTS = Arrays.asList(
            EDITABLE_FORM_EE
    );

    @Value( "${frontend.address}" )
    private String frontendAddress;
    private final ObjectMapper objectMapper;
    private final TextBlockService textBlockService;
    private final CreateLoanPartiesService createLoanPartiesService;
    private final CreateLoanConditionsService createLoanConditionsService;
    private final CreateLoanScheduleService createLoanScheduleService;
    private final CreateCellService createCellService;

    @Override
    public void generatePdf(TemplateCode templateCode, LanguageCode languageCode, String inputData, OutputStream outputStream) {
        // https://itextpdf.com/en/resources/examples/itext-5-legacy/create-fields-table
        try (Document document = new Document()) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            PdfPTable table = new PdfPTable(2);
            PdfPCell cell;
            cell = new PdfPCell(new Phrase("Name:"));
            table.addCell(cell);
            cell = new PdfPCell();
            cell.setCellEvent(new TextInputCellField("name"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Address"));
            table.addCell(cell);
            cell = new PdfPCell();
            cell.setCellEvent(new TextInputCellField("address"));
            table.addCell(cell);
            table.addCell(createCellService.createEmptyCellWithNoStyles());
            table.addCell(createCellService.createEmptyCellWithNoStyles());
            document.add(table);

            PdfFormField radiogroup = PdfFormField.createRadioButton(writer, true);
            radiogroup.setFieldName("Language");
            table = new PdfPTable(2);
            cell = new PdfPCell(new Phrase("English:"));
            table.addCell(cell);
            cell = new PdfPCell();
            cell.setCellEvent(new RadioCellField(radiogroup, "english"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("French:"));
            table.addCell(cell);
            cell = new PdfPCell();
            cell.setCellEvent(new RadioCellField(radiogroup, "french"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Dutch:"));
            table.addCell(cell);
            cell = new PdfPCell();
            cell.setCellEvent(new RadioCellField(radiogroup, "dutch"));
            table.addCell(cell);
            document.add(table);
            writer.addAnnotation(radiogroup);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<TemplateCode> getSupportedPrintouts() {
        return SUPPORTED_PRINTOUTS;
    }

}
