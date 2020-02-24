package generate.pdf.openpdf.service.printout;

import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrintoutGeneratorProvider {

    private final List<PdfGenerator> pdfGenerators;

    /**
     * Find correct
     * @param templateCode Unique identifier for template.
     * @return A class extending BasePdfGenerator, which is for generating a PDF using template code from @param.
     */
    public PdfGenerator getPrintoutGeneratorForGivenPrintoutType(TemplateCode templateCode) {
        List<PdfGenerator> matchingPdfGenerators = pdfGenerators.stream()
                .filter(generator -> generator.getSupportedPrintouts().contains(templateCode))
                .collect(Collectors.toList());

        if (matchingPdfGenerators.size() != 1) {
            throw new BadRequestException("Exactly one generator must exist for one printout type!");
        }

        return matchingPdfGenerators.get(0);
    }

}
