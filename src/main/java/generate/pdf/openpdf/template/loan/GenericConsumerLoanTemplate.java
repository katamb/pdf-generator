package generate.pdf.openpdf.template.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.template.loan.conditions.CreateLoanConditionsService;
import generate.pdf.openpdf.template.loan.dto.LoanContractInputDto;
import generate.pdf.openpdf.enums.PrintoutType;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.service.printout.PdfGenerator;
import generate.pdf.openpdf.template.loan.parties.CreateLoanPartiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static generate.pdf.openpdf.enums.PrintoutType.*;

@Service
@RequiredArgsConstructor
public class GenericConsumerLoanTemplate implements PdfGenerator {

    private static final List<PrintoutType> SUPPORTED_PRINTOUTS = Arrays.asList(
            PRIVATE_SMALL_LOAN_CONTRACT_EE
    );
    private final CreateLoanPartiesService createLoanPartiesService;
    private final CreateLoanConditionsService createLoanConditionsService;
    private final TextBlockService textBlockService;
    private final ObjectMapper objectMapper;

    @Override
    public String generatePrintoutAndReturnFileName(PrintoutType printoutType,
                                                    LanguageCode languageCode,
                                                    String inputData) {
        // Get json input as Java object
        LoanContractInputDto loanContractInputDto = mapStringToObject(inputData);
        Map<String, Object> map = objectMapper.convertValue(loanContractInputDto, Map.class);
        // Get static text with design advice from db
        Map<String, TextBlockWithStyle> textBlocksWithStyle =
                textBlockService.getTextsByGroupAndLanguage(printoutType, languageCode);
        String fileName = "Loan-contract-v1.pdf";  // todo real filename

        // 1: creation of a document-object
        try (Document document = new Document()) {
            // 2: we create a writer that listens to the document
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            // 3: add header and footer todo

            // 4: we open the document
            document.open();
            // 5: first paragraph
            createLoanPartiesService.createPartiesData(document, textBlocksWithStyle, loanContractInputDto, map);
            // 6: main conditions
            createLoanConditionsService.createMainConditions(document, textBlocksWithStyle, loanContractInputDto, map);
        } catch (FileNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
        return fileName;
    }

    @Override
    public byte[] generatePrintoutAndReturnFileName(PrintoutType printoutType,
                                                    LanguageCode languageCode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Get static text with design advice from db
        Map<String, TextBlockWithStyle> textBlocksWithStyle =
                textBlockService.getTextsByGroupAndLanguage(printoutType, languageCode);
        LoanContractInputDto loanContractInputDto = new LoanContractInputDto();

        // 1: creation of a document-object
        try (Document document = new Document()) {
            // 2: we create a writer that listens to the document
            PdfWriter.getInstance(document, outputStream);
            // 3: add header and footer todo

            // 4: we open the document
            document.open();
            // 5: first paragraph
            createLoanPartiesService.createPartiesData(document, textBlocksWithStyle, loanContractInputDto, null);
            // 6: main conditions
            createLoanConditionsService.createMainConditions(document, textBlocksWithStyle, loanContractInputDto, null);
        } catch (NullPointerException e) {
            throw new BadRequestException(e.getMessage());
        }
        return outputStream.toByteArray();
    }

    private LoanContractInputDto mapStringToObject(String inputData) {
        try {
            return objectMapper.readValue(inputData, LoanContractInputDto.class);
        } catch (Exception e) {
            throw new BadRequestException("Failed to map input data to corresponding Java class!");
        }
    }

    @Override
    public List<PrintoutType> getSupportedPrintouts() {
        return SUPPORTED_PRINTOUTS;
    }

}
