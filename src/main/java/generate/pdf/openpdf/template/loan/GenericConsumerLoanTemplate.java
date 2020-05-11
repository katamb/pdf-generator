package generate.pdf.openpdf.template.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import generate.pdf.openpdf.config.EnvironmentVariableProvider;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.printout.PdfGenerator;
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
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static generate.pdf.openpdf.enums.TemplateCode.PRIVATE_CAR_LOAN_CONTRACT_EE;
import static generate.pdf.openpdf.enums.TemplateCode.PRIVATE_SMALL_LOAN_CONTRACT_EE;

@Service
public class GenericConsumerLoanTemplate extends PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(GenericConsumerLoanTemplate.class);
    private static final List<TemplateCode> SUPPORTED_PRINTOUTS = Arrays.asList(
            PRIVATE_SMALL_LOAN_CONTRACT_EE,
            PRIVATE_CAR_LOAN_CONTRACT_EE
    );

    private final ObjectMapper objectMapper;
    private final CreateCellService createCellService;
    private final CreateSimpleTextService createSimpleTextService;
    private final CreateLoanPartiesService createLoanPartiesService;
    private final CreateLoanScheduleService createLoanScheduleService;
    private final CreateLoanConditionsService createLoanConditionsService;

    public GenericConsumerLoanTemplate(
            ObjectMapper objectMapper,
            StartupConfig startupConfig,
            TextBlockService textBlockService,
            CreateCellService createCellService,
            CreateSimpleTextService createSimpleTextService,
            CreateLoanPartiesService createLoanPartiesService,
            CreateLoanScheduleService createLoanScheduleService,
            CreateLoanConditionsService createLoanConditionsService,
            EnvironmentVariableProvider environmentVariableProvider
    ) {
        super(startupConfig, objectMapper, textBlockService, environmentVariableProvider);
        this.objectMapper = objectMapper;
        this.createCellService = createCellService;
        this.createSimpleTextService = createSimpleTextService;
        this.createLoanPartiesService = createLoanPartiesService;
        this.createLoanScheduleService = createLoanScheduleService;
        this.createLoanConditionsService = createLoanConditionsService;
    }

    @Override
    public void generatePdf(
            Document document,
            Map<String, Object> dynamicData,
            Map<String, TemplateTextDto> templateTexts,
            String editingUrl,
            String inputData,
            OutputStream outputStream
    ) {
        Font font = new Font(Font.HELVETICA);
        document.setPageSize(PageSize.A4);
        document.setMargins(36, 36, 84, 60);

        LoanContractInputDto loanContractInputDto = inputData != null ? mapStringToObject(inputData) : null;

        // Create a writer that listens to the document
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        // Add header and footer to every page
        writer.setPageEvent(new HeaderFooterPageEvent(createCellService, templateTexts, dynamicData, editingUrl, font));
        // Open the document
        document.open();
        // Create document content
        createSimpleTextService.createHeaderCell(document, templateTexts, dynamicData, editingUrl, font);
        createLoanPartiesService.createPartiesData(document, templateTexts, dynamicData, editingUrl, font);
        createLoanConditionsService.createMainConditions(document, templateTexts, loanContractInputDto, dynamicData, editingUrl, font);
        // New page
        document.newPage();
        createLoanScheduleService.createSchedule(document, templateTexts, loanContractInputDto, editingUrl, font);
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

    @Override
    public List<TemplateCode> getSupportedPrintouts() {
        return SUPPORTED_PRINTOUTS;
    }

}
