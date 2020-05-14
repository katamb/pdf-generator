package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.TemplateTextDto;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class NumberingService {

    public String getNumberForTextBlock(TemplateTextDto text, LinkedList<Integer> numberingMemory) {
        if (!text.isNumbering() || text.getNumberingLevel() == null) {
            return "";
        }
        if (numberingMemory.size() == text.getNumberingLevel()) {
            Integer number = numberingMemory.removeLast();
            numberingMemory.addLast(number + 1);
            StringBuilder sb = new StringBuilder();
            numberingMemory.forEach(num -> sb.append(num).append("."));
            return sb.toString();
        }
        if (numberingMemory.size() > text.getNumberingLevel()) {
            numberingMemory.removeLast();
            return getNumberForTextBlock(text, numberingMemory);
        }
        if (numberingMemory.size() < text.getNumberingLevel()) {
            numberingMemory.addLast(0);
            return getNumberForTextBlock(text, numberingMemory);
        }
        return "";
    }

}
