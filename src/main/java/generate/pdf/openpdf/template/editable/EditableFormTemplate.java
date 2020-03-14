package generate.pdf.openpdf.template.editable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.printout.BasePdfGenerator;
import generate.pdf.openpdf.template.editable.dto.FormInputDto;
import generate.pdf.openpdf.template.editable.form.CreateFormFieldsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static generate.pdf.openpdf.enums.TemplateCode.EDITABLE_FORM_EE;

@Service
public class EditableFormTemplate extends BasePdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(EditableFormTemplate.class);
    private static final List<TemplateCode> SUPPORTED_PRINTOUTS = Arrays.asList(
            EDITABLE_FORM_EE
    );

    private final ObjectMapper objectMapper;
    private final TextBlockService textBlockService;
    private final CreateFormFieldsService createFormFieldsService;

    @Value( "${front-end.address}" )
    private String frontendAddress;
    private Font font;

    public EditableFormTemplate(
            StartupConfig startupConfig,
            ObjectMapper objectMapper,
            TextBlockService textBlockService,
            CreateFormFieldsService createFormFieldsService
    ) {
        super(startupConfig);
        this.objectMapper = objectMapper;
        this.textBlockService = textBlockService;
        this.createFormFieldsService = createFormFieldsService;
    }

    /**
     * Created with help from: https://itextpdf.com/en/resources/examples/itext-5-legacy/create-fields-table
     */
    @Override
    public void generatePdf(TemplateCode templateCode, LanguageCode languageCode, String inputData, OutputStream outputStream) {
        font = new Font(Font.HELVETICA);
        FormInputDto formInputDto = null;
        Map<String, Object> inputValueMap = null;
        if (inputData != null) {
            formInputDto = mapStringToObject(inputData);
            inputValueMap = mapObjectToMap(formInputDto);
        }
        Map<String, TemplateTextBlock> templateTextBlockMap =
                textBlockService.getTextsByTemplateAndLanguage(templateCode, languageCode);
        String url = inputData == null
                ? frontendAddress + "/#/edit-pdf/" + templateCode.name() + "/" + languageCode.toString() + "/"
                : null;

        try (Document document = new Document(PageSize.A4, 36, 36, 60, 48)) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            createFormFieldsService.createForm(writer, document, templateTextBlockMap, inputValueMap, url, font);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            throw new BadRequestException(e.getMessage());
        }
    }

    private FormInputDto mapStringToObject(String inputData) {
        try {
            return objectMapper.readValue(inputData, FormInputDto.class);
        } catch (Exception e) {
            String message = "Failed to map input data to corresponding Java class!";
            logger.warn(message, e);
            throw new BadRequestException(message);
        }
    }

    private Map<String, Object> mapObjectToMap(FormInputDto formInputDto) {
        try {
            return objectMapper.convertValue(formInputDto, Map.class);
        } catch (Exception e) {
            String message = "Failed to map Java class to Map!";
            logger.warn(message, e);
            throw new BadRequestException(message);
        }
    }

    @Override
    public List<TemplateCode> getSupportedPrintouts() {
        return SUPPORTED_PRINTOUTS;
    }

}
