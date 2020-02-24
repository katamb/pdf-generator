package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.service.printout.PdfGenerator;
import generate.pdf.openpdf.service.printout.PrintoutGeneratorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @CrossOrigin
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
