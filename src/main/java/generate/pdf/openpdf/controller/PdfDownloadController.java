package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.dto.FileResponseDto;
import generate.pdf.openpdf.service.PdfStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/download")
public class PdfDownloadController {

    private final PdfStorageService pdfStorageService;

    @GetMapping(value = "pdf/{fileName}")
    public FileResponseDto downloadPdfFile(@PathVariable String fileName) {
        return pdfStorageService.loadFileAsResource(fileName);
    }

}
