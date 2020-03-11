package generate.pdf.openpdf.service.printout;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * All PDF templates need to extend this abstract class.
 */
@RequiredArgsConstructor
public abstract class BasePdfGenerator implements PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(BasePdfGenerator.class);
    private static final String PDF_EXTENSION = ".pdf";
    @Value( "${storage.pdf.folder}" )
    private String fileLocation;

    /**
     * This method is called by another service. Generates a file and returns its name.
     * @param templateCode Enum with the template code.
     * @param languageCode In which language should the printout be generated.
     * @param inputData Data from
     * @return Generated file as byte-array.
     */
    @Override
    public String generatePrintoutAndReturnFileName(TemplateCode templateCode, LanguageCode languageCode, String inputData) {
        String fileName = fileLocation + UUID.randomUUID().toString() + PDF_EXTENSION;
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            generatePdf(templateCode, languageCode, inputData, outputStream);
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
    @Override
    public byte[] generatePrintoutAndReturnFileName(TemplateCode templateCode, LanguageCode languageCode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        generatePdf(templateCode, languageCode, null, outputStream);
        return outputStream.toByteArray();
    }

}
