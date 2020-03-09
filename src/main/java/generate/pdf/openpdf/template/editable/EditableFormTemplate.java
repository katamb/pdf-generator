package generate.pdf.openpdf.template.editable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.*;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.printout.BasePdfGenerator;
import generate.pdf.openpdf.template.editable.dto.FormInputDto;
import generate.pdf.openpdf.template.editable.form.CreateFormFieldsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    private final ObjectMapper objectMapper;
    private final TextBlockService textBlockService;
    private final CreateFormFieldsService createFormFieldsService;

    @Value( "${frontend.address}" )
    private String frontendAddress;
    private Font font;

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
                ? frontendAddress + templateCode.name() + "/" + languageCode.toString() + "/"
                : null;

        try (Document document = new Document(PageSize.A4, 36, 36, 60, 48)) {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            createFormFieldsService.createForm(writer, document, templateTextBlockMap, inputValueMap, url, font);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new BadRequestException(e.getMessage());
        }
    }

    private FormInputDto mapStringToObject(String inputData) {
        try {
            return objectMapper.readValue(inputData, FormInputDto.class);
        } catch (Exception e) {
            String message = "Failed to map input data to corresponding Java class!";
            logger.log(Level.WARNING, message, e);
            throw new BadRequestException(message);
        }
    }

    private Map<String, Object> mapObjectToMap(FormInputDto formInputDto) {
        try {
            return objectMapper.convertValue(formInputDto, Map.class);
        } catch (Exception e) {
            String message = "Failed to map Java class to Map!";
            logger.log(Level.WARNING, message, e);
            throw new BadRequestException(message);
        }
    }

    @Override
    public List<TemplateCode> getSupportedPrintouts() {
        return SUPPORTED_PRINTOUTS;
    }

}
