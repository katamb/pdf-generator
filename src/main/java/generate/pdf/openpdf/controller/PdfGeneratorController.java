package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.service.printout.PdfGenerator;
import generate.pdf.openpdf.service.printout.PrintoutGeneratorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/file-generator")
public class PdfGeneratorController {

    private final PrintoutGeneratorProvider printoutGeneratorProvider;

    @PostMapping("generate/pdf/{templateCode}/{languageCode}")
    public String pdfGenerator(
            @PathVariable TemplateCode templateCode,
            @PathVariable LanguageCode languageCode,
            @RequestBody String inputData
    ) {
        PdfGenerator pdfGenerator = printoutGeneratorProvider.getPrintoutGeneratorForGivenPrintoutType(templateCode);
        return pdfGenerator.generatePrintoutAndReturnFileName(templateCode, languageCode, inputData);
    }

    /**
     * This is only for back-office, as the pdf generated under this endpoint has links in text for editing purposes
     */
    @GetMapping(value = "edit/pdf/{templateCode}/{languageCode}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdfEditor(
            @PathVariable TemplateCode templateCode,
            @PathVariable LanguageCode languageCode
    ) {
        PdfGenerator pdfGenerator = printoutGeneratorProvider.getPrintoutGeneratorForGivenPrintoutType(templateCode);
        byte[] pdfFile = pdfGenerator.generatePrintoutAndReturnFileName(templateCode, languageCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(pdfFile, headers, HttpStatus.OK);
    }

}
