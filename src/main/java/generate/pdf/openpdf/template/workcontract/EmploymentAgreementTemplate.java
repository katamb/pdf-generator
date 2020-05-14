package generate.pdf.openpdf.template.workcontract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import generate.pdf.openpdf.config.EnvironmentVariableProvider;
import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.service.TextBlockService;
import generate.pdf.openpdf.service.printout.PdfGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static generate.pdf.openpdf.enums.TemplateCode.EMPLOYMENT_CONTRACT_EN;

@Service
public class EmploymentAgreementTemplate extends PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(EmploymentAgreementTemplate.class);
    private static final List<TemplateCode> SUPPORTED_PRINTOUTS = Arrays.asList(
            EMPLOYMENT_CONTRACT_EN
    );

    private final CreateEmploymentAgreementEntriesService createEmploymentAgreementEntriesService;

    public EmploymentAgreementTemplate(
            ObjectMapper objectMapper,
            StartupConfig startupConfig,
            TextBlockService textBlockService,
            CreateEmploymentAgreementEntriesService createEmploymentAgreementEntriesService,
            EnvironmentVariableProvider environmentVariableProvider
    ) {
        super(startupConfig, objectMapper, textBlockService, environmentVariableProvider);
        this.createEmploymentAgreementEntriesService = createEmploymentAgreementEntriesService;
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
        document.setMargins(36, 36, 60, 48);

        // Create a writer that listens to the document
        PdfWriter.getInstance(document, outputStream);
        // Open the document
        document.open();
        // Create document content
        createEmploymentAgreementEntriesService.createEntries(document, templateTexts, dynamicData, editingUrl, font);
    }

    @Override
    public List<TemplateCode> getSupportedPrintouts() {
        return SUPPORTED_PRINTOUTS;
    }

}
