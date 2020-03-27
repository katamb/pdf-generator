package generate.pdf.openpdf.service.printout;

import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import static generate.pdf.openpdf.enums.TemplateCode.DUMMY_TEMPLATE_FOR_TESTING;

@Service
public class DummyPrintoutGenerator extends PdfGenerator {

    public DummyPrintoutGenerator(StartupConfig startupConfig) {
        super(startupConfig);
    }

    @Override
    public void generatePdf(TemplateCode templateCode, LanguageCode languageCode, String inputData, OutputStream outputStream) {
    }

    @Override
    public List<TemplateCode> getSupportedPrintouts() {
        return Collections.singletonList(DUMMY_TEMPLATE_FOR_TESTING);
    }

}
