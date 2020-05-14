package generate.pdf.openpdf.template.loan.text;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.service.table.CreateCellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateSimpleTextService {

    private final CreateCellService createCellService;

    public void createHeaderCell(
            Document document,
            Map<String, TemplateTextDto> templateTextBlock,
            Map<String, Object> inputDataAsMap,
            String url,
            Font font
    ) {
        PdfPTable table = new PdfPTable(1);

        table.addCell(createCellService.createCellAndInsertDynamicData(font, templateTextBlock.get("LOAN_CONTRACT"), inputDataAsMap, url));

        document.add(table);
    }

}
