package generate.pdf.openpdf.template.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.exception.PdfGenerationException;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.printout.BasePdfGenerator;
import generate.pdf.openpdf.service.table.CreateCellService;
import generate.pdf.openpdf.template.loan.conditions.CreateLoanConditionsService;
import generate.pdf.openpdf.template.loan.dto.LoanContractInputDto;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.template.loan.header.HeaderFooterPageEvent;
import generate.pdf.openpdf.template.loan.parties.CreateLoanPartiesService;
import generate.pdf.openpdf.template.loan.schedule.CreateLoanScheduleService;
import generate.pdf.openpdf.template.loan.text.CreateSimpleTextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static generate.pdf.openpdf.enums.TemplateCode.PRIVATE_CAR_LOAN_CONTRACT_EE;
import static generate.pdf.openpdf.enums.TemplateCode.PRIVATE_SMALL_LOAN_CONTRACT_EE;

@Service
public class GenericConsumerLoanTemplate extends BasePdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(GenericConsumerLoanTemplate.class);
    private static final List<TemplateCode> SUPPORTED_PRINTOUTS = Arrays.asList(
            PRIVATE_SMALL_LOAN_CONTRACT_EE,
            PRIVATE_CAR_LOAN_CONTRACT_EE
    );

    private final ObjectMapper objectMapper;
    private final TextBlockService textBlockService;
    private final CreateLoanPartiesService createLoanPartiesService;
    private final CreateLoanConditionsService createLoanConditionsService;
    private final CreateLoanScheduleService createLoanScheduleService;
    private final CreateCellService createCellService;
    private final CreateSimpleTextService createSimpleTextService;

    @Value("${front-end.address}")
    private String frontendAddress;

    public GenericConsumerLoanTemplate(
            StartupConfig startupConfig,
            ObjectMapper objectMapper,
            TextBlockService textBlockService,
            CreateLoanPartiesService createLoanPartiesService,
            CreateLoanConditionsService createLoanConditionsService,
            CreateLoanScheduleService createLoanScheduleService,
            CreateCellService createCellService,
            CreateSimpleTextService createSimpleTextService
    ) {
        super(startupConfig);
        this.objectMapper = objectMapper;
        this.textBlockService = textBlockService;
        this.createLoanPartiesService = createLoanPartiesService;
        this.createLoanConditionsService = createLoanConditionsService;
        this.createLoanScheduleService = createLoanScheduleService;
        this.createCellService = createCellService;
        this.createSimpleTextService = createSimpleTextService;
    }

    @Override
    public void generatePdf(TemplateCode templateCode, LanguageCode languageCode, String inputData, OutputStream outputStream) {
        Font font = new Font(Font.HELVETICA);
        LoanContractInputDto loanContractInputDto = null;
        Map<String, Object> inputValueMap = null;
        if (inputData != null) {
            loanContractInputDto = mapStringToObject(inputData);
            inputValueMap = mapObjectToMap(loanContractInputDto);
        }
        // Get static text with design advice from db
        Map<String, TemplateTextBlock> templateTextBlockMap =
                textBlockService.getTextsByTemplateAndLanguage(templateCode, languageCode);
        // Url is only set if in editing mode (which means input-data is missing)
        String url = inputData == null
                ? frontendAddress + "/#/edit-pdf/" + templateCode.name() + "/" + languageCode.toString() + "/"
                : null;

        // 1: Create document
        try (Document document = new Document(PageSize.A4, 36, 36, 84, 60)) {
            // 2: Create a writer that listens to the document
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            // 3: Add header and footer
            writer.setPageEvent(new HeaderFooterPageEvent(createCellService, templateTextBlockMap, inputValueMap, url, font));
            // 4: Open the document
            document.open();
            // 5: Create document content
            createSimpleTextService.createSingleCell(document, templateTextBlockMap.get("LOAN_CONTRACT"), inputValueMap, url, font);
            createLoanPartiesService.createPartiesData(document, templateTextBlockMap, inputValueMap, url, font);
            createLoanConditionsService.createMainConditions(document, templateTextBlockMap, loanContractInputDto, inputValueMap, url, font);
            document.newPage(); // New page
            createLoanScheduleService.createSchedule(document, templateTextBlockMap, loanContractInputDto, url, font);
        } catch (DocumentException e) {
            logger.warn(e.getMessage(), e);
            throw new PdfGenerationException(e.getMessage(), e);
        }
    }

    private LoanContractInputDto mapStringToObject(String inputData) {
        try {
            return objectMapper.readValue(inputData, LoanContractInputDto.class);
        } catch (Exception e) {
            String message = "Failed to map input data to corresponding Java class!";
            logger.warn(message, e);
            throw new BadRequestException(message);
        }
    }

    private Map<String, Object> mapObjectToMap(LoanContractInputDto loanContractInputDto) {
        try {
            return objectMapper.convertValue(loanContractInputDto, Map.class);
        } catch (Exception e) {
            String message = "Failed to map Java class to Map!";
            logger.warn(message, e);
            throw new BadRequestException(message);
        }
    }

    @Override
    public List<TemplateCode> getSupportedPrintouts() {
        return SUPPORTED_PRINTOUTS;
    }

}
