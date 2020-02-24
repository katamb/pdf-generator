package generate.pdf.openpdf.template.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.printout.BasePdfGenerator;
import generate.pdf.openpdf.template.loan.conditions.CreateLoanConditionsService;
import generate.pdf.openpdf.template.loan.dto.LoanContractInputDto;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.template.loan.parties.CreateLoanPartiesService;
import generate.pdf.openpdf.template.loan.schedule.CreateLoanScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static generate.pdf.openpdf.enums.TemplateCode.PRIVATE_SMALL_LOAN_CONTRACT_EE;

@Service
@RequiredArgsConstructor
public class GenericConsumerLoanTemplate extends BasePdfGenerator {

    private static final Logger logger = Logger.getLogger(String.valueOf(GenericConsumerLoanTemplate.class));
    private static final List<TemplateCode> SUPPORTED_PRINTOUTS = Arrays.asList(
            PRIVATE_SMALL_LOAN_CONTRACT_EE
    );

    @Value( "${frontend.address}" )
    private String frontendAddress;
    private final ObjectMapper objectMapper;
    private final TextBlockService textBlockService;
    private final CreateLoanPartiesService createLoanPartiesService;
    private final CreateLoanConditionsService createLoanConditionsService;
    private final CreateLoanScheduleService createLoanScheduleService;

    @Override
    public void generatePdf(TemplateCode templateCode, LanguageCode languageCode, String inputData, OutputStream outputStream) {
        LoanContractInputDto loanContractInputDto = null;
        Map<String, Object> inputValueMap = null;
        if (inputData != null) {
            loanContractInputDto = mapStringToObject(inputData);
            inputValueMap = mapObjectToMap(loanContractInputDto);
        }
        // Get static text with design advice from db
        Map<String, TextBlockWithStyle> textBlocksWithStyle =
                textBlockService.getTextsByGroupAndLanguage(templateCode, languageCode);
        String url = inputData == null
                ? frontendAddress + templateCode.name() + "/" + languageCode.toString() + "/"
                : null;

        try (Document document = new Document()) {
            // 2: we create a writer that listens to the document
            PdfWriter.getInstance(document, outputStream);
            // 3: add header and footer todo

            // 4: we open the document
            document.open();
            // 5: first paragraph
            createLoanPartiesService.createPartiesData(document, textBlocksWithStyle, loanContractInputDto, inputValueMap, url);
            // 6: main conditions
            createLoanConditionsService.createMainConditions(document, textBlocksWithStyle, loanContractInputDto, inputValueMap, url);
            // New page
            document.newPage();
            // 6: schedule
            createLoanScheduleService.createSchedule(document, textBlocksWithStyle, loanContractInputDto, inputValueMap, url);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new BadRequestException(e.getMessage());
        }
    }

    private LoanContractInputDto mapStringToObject(String inputData) {
        try {
            return objectMapper.readValue(inputData, LoanContractInputDto.class);
        } catch (Exception e) {
            String message = "Failed to map input data to corresponding Java class!";
            logger.log(Level.WARNING, message, e);
            throw new BadRequestException(message);
        }
    }

    private Map<String, Object> mapObjectToMap(LoanContractInputDto loanContractInputDto) {
        try {
            return objectMapper.convertValue(loanContractInputDto, Map.class);
        } catch (Exception e) {
            String message = "Failed to map Java class to Map!";
            logger.log(Level.WARNING, message, e);
            throw new BadRequestException(message);
        }
    }

    @Override
    public List<TemplateCode> getSupportedPrintouts() {
        return SUPPORTED_PRINTOUTS;
    }

}
