package generate.pdf.openpdf.template.editable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import generate.pdf.openpdf.config.EnvironmentVariableProvider;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.printout.PdfGenerator;
import generate.pdf.openpdf.template.editable.form.CreateFormFieldsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static generate.pdf.openpdf.enums.TemplateCode.EDITABLE_FORM_EE;

@Service
public class EditableFormTemplate extends PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(EditableFormTemplate.class);
    private static final List<TemplateCode> SUPPORTED_PRINTOUTS = Arrays.asList(
            EDITABLE_FORM_EE
    );

    private final CreateFormFieldsService createFormFieldsService;

    public EditableFormTemplate(
            ObjectMapper objectMapper,
            StartupConfig startupConfig,
            TextBlockService textBlockService,
            CreateFormFieldsService createFormFieldsService,
            EnvironmentVariableProvider environmentVariableProvider
    ) {
        super(startupConfig, objectMapper, textBlockService, environmentVariableProvider);
        this.createFormFieldsService = createFormFieldsService;
    }

    /**
     * Created with help from: https://itextpdf.com/en/resources/examples/itext-5-legacy/create-fields-table
     */
    @Override
    public void generatePdf(
            Document document,
            Map<String, Object> dynamicData,
            Map<String, TemplateTextDto> templateTexts,
            String editingUrl,
            String inputData,
            OutputStream outputStream
    ) {
        Font font = new Font(Font.HELVETICA);
        document.setPageSize(PageSize.A4);
        document.setMargins(36, 36, 60, 48);

        // Create a writer that listens to the document
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        // Open the document
        document.open();
        // Create document content
        createFormFieldsService.createForm(writer, document, templateTexts, dynamicData, editingUrl, font);
    }

    @Override
    public List<TemplateCode> getSupportedPrintouts() {
        return SUPPORTED_PRINTOUTS;
    }

}
