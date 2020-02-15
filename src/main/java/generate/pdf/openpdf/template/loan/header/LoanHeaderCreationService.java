package generate.pdf.openpdf.template.loan.header;

import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import org.springframework.stereotype.Service;

@Service
public class LoanHeaderCreationService {

    public HeaderFooter getHeaderForBasicLoanContract(String contractNumber) {
        HeaderFooter footer = new HeaderFooter(new Phrase("- "), new Phrase(" -"));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setBorder(Rectangle.NO_BORDER);
        return footer;
    }

}
