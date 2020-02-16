package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.PrintoutType;
import generate.pdf.openpdf.service.printout.PdfGenerator;
import generate.pdf.openpdf.service.printout.PrintoutGeneratorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/file-generator")
public class PdfGeneratorController {

    private final PrintoutGeneratorProvider printoutGeneratorProvider;

    @PostMapping("generate/pdf/{printoutType}/{languageCode}")
    public String pdfGenerator(@PathVariable PrintoutType printoutType,
                             @PathVariable LanguageCode languageCode,
                             @RequestBody String inputData) {
        PdfGenerator pdfGenerator = printoutGeneratorProvider.getPrintoutGeneratorForGivenPrintoutType(printoutType);
        return pdfGenerator.generatePrintoutAndReturnFileName(printoutType, languageCode, inputData);
    }

    @CrossOrigin
    @GetMapping(value = "edit/pdf/{printoutType}/{languageCode}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdfEditor(@PathVariable PrintoutType printoutType,
                                            @PathVariable LanguageCode languageCode) {
        PdfGenerator pdfGenerator = printoutGeneratorProvider.getPrintoutGeneratorForGivenPrintoutType(printoutType);
        byte[] pdfFile = pdfGenerator.generatePrintoutAndReturnFileName(printoutType, languageCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(pdfFile, headers, HttpStatus.OK);
    }
}
