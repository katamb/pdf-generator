package generate.pdf.openpdf.service.printout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import generate.pdf.openpdf.config.EnvironmentVariableProvider;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.exception.InternalServerException;
import generate.pdf.openpdf.exception.PdfGenerationException;
import generate.pdf.openpdf.service.TextBlockService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * All PDF templates need to extend this abstract class.
 */
@Transactional
@RequiredArgsConstructor
public abstract class PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);
    private static final String PDF_EXTENSION = ".pdf";

    private final StartupConfig startupConfig;
    private final ObjectMapper objectMapper;
    private final TextBlockService textBlockService;
    private final EnvironmentVariableProvider environmentVariableProvider;

    /**
     * This method is called by another service. Generates a file and returns its name.
     * @param templateCode Enum with the template code.
     * @param languageCode In which language should the printout be generated.
     * @param inputData Data from
     * @return Generated file as byte-array.
     */
    public String generatePrintoutAndReturnFileName(
            TemplateCode templateCode,
            LanguageCode languageCode,
            String inputData
    ) {
        String fileName = UUID.randomUUID().toString() + PDF_EXTENSION;
        String filePath = startupConfig.getPdfDirectory() + fileName;
        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            startPdfGeneration(templateCode, languageCode, inputData, outputStream, false);
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage(), e);
            throw new InternalServerException(e.getMessage());
        }

        return fileName;
    }

    /**
     * This method is used to generate PDF with placeholder values for back-office.
     * @param templateCode Enum with the template code.
     * @param languageCode In which language should the printout be generated.
     * @return Generated file as byte-array.
     */
    public byte[] generatePrintoutAndReturnFileName(TemplateCode templateCode, LanguageCode languageCode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        startPdfGeneration(templateCode, languageCode, null, outputStream, true);
        return outputStream.toByteArray();
    }

    private void startPdfGeneration(
            TemplateCode templateCode,
            LanguageCode languageCode,
            String inputData,
            OutputStream outputStream,
            boolean editMode
    ) {
        // Convert dynamic input from JSON string to Java map
        Map<String, Object> dynamicData = editMode ? null : stringToMap(inputData);

        // Get static text with design advice from db
        Map<String, TemplateTextDto> templateTexts =
                textBlockService.getTextsByTemplateAndLanguage(templateCode, languageCode);

        // Url is only set if in editing mode (which means input-data is missing)
        String editingUrl = editMode ? createEditUrlPath(templateCode, languageCode) : null;

        try (Document document = new Document()) {
            generatePdf(document, dynamicData, templateTexts, editingUrl, inputData, outputStream);
        } catch (DocumentException e) {
            logger.warn(e.getMessage(), e);
            throw new PdfGenerationException(e.getMessage(), e);
        }
    }

    private Map<String, Object> stringToMap(String inputData) {
        try {
            return objectMapper.readValue(inputData, Map.class);
        } catch (Exception e) {
            String message = "Failed to map input data to Java map!";
            logger.warn(message, e);
            throw new BadRequestException(message);
        }
    }

    private String createEditUrlPath(TemplateCode templateCode, LanguageCode languageCode) {
        return environmentVariableProvider.getFrontendAddress() + environmentVariableProvider.getFrontendEditPath()
                + templateCode.name() + "/" + languageCode.name() + "/";
    }

    public abstract void generatePdf(
            Document document,
            Map<String, Object> dynamicData,
            Map<String, TemplateTextDto> templateTexts,
            String editingUrl,
            String inputData,
            OutputStream outputStream
    );

    public abstract List<TemplateCode> getSupportedPrintouts();

}
