package generate.pdf.openpdf.service.printout;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.PrintoutType;

import java.util.List;

/**
 * All PDF templates need to implement this interface.
 */
public interface PdfGenerator {

    List<PrintoutType> getSupportedPrintouts();

    String generatePrintoutAndReturnFileName(PrintoutType printoutType, LanguageCode languageCode, String inputData);

    byte[] generatePrintoutAndReturnFileName(PrintoutType printoutType, LanguageCode languageCode);

}
