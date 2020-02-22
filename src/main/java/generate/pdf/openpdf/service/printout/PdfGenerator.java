package generate.pdf.openpdf.service.printout;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;

import java.io.OutputStream;
import java.util.List;

public interface PdfGenerator {

    List<TemplateCode> getSupportedPrintouts();

    String generatePrintoutAndReturnFileName(TemplateCode templateCode, LanguageCode languageCode, String inputData);

    byte[] generatePrintoutAndReturnFileName(TemplateCode templateCode, LanguageCode languageCode);

    void generatePdf(TemplateCode templateCode, LanguageCode languageCode, String inputData, OutputStream outputStream);

}
