package generate.pdf.openpdf.service.printout;

import generate.pdf.openpdf.enums.PrintoutType;
import generate.pdf.openpdf.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrintoutGeneratorProvider {

    private final List<PdfGenerator> pdfGenerators;

    public PdfGenerator getPrintoutGeneratorForGivenPrintoutType(PrintoutType printoutType) {
        List<PdfGenerator> matchingPdfGenerators = pdfGenerators.stream()
                .filter(generator -> generator.getSupportedPrintouts().contains(printoutType))
                .collect(Collectors.toList());

        if (matchingPdfGenerators.size() != 1) {
            throw new BadRequestException("Exactly one generator must exist for one printout type!");
        }

        return matchingPdfGenerators.get(0);
    }

}
