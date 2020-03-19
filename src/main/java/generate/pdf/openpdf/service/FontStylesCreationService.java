package generate.pdf.openpdf.service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FontStylesCreationService {

    private static final List<String> SPECIAL_SEQUENCE_LIST = Arrays.asList(
            "<u>", "</u>", "<i>", "</i>", "<b>", "</b>", "<s>", "</s>"
    );

    private HashMap<Integer, String> sequenceStartIndexToSequenceMap;

    public Phrase createPhraseWithInlineStyles(Font regularFont, String text) {
        sequenceStartIndexToSequenceMap = new HashMap<>();

        for (String specialSequence : SPECIAL_SEQUENCE_LIST) {
            addToMap(specialSequence, text);
        }
        List<Integer> indexes = sequenceStartIndexToSequenceMap.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        Integer offset = 0;
        Phrase phrase = new Phrase();
        Font currentFont = new Font(regularFont);
        currentFont.setStyle(Font.NORMAL);

        Integer previousIndex = 0;
        for (Integer index : indexes) {
            String textPart = text.substring(previousIndex + offset, index);
            Chunk chunk = new Chunk(textPart, currentFont);
            phrase.add(chunk);
            previousIndex = index;
            String specialSequence = sequenceStartIndexToSequenceMap.get(index);
            currentFont = getFontFromSequence(currentFont, specialSequence);
            offset = sequenceStartIndexToSequenceMap.get(index).length();
        }
        String textPart = text.substring(previousIndex + offset);
        Chunk chunk = new Chunk(textPart, currentFont);
        phrase.add(chunk);

        return phrase;
    }

    private void addToMap(String specialSequence, String text) {
        Matcher matcher = Pattern.compile(specialSequence).matcher(text);
        while (matcher.find()) {
            sequenceStartIndexToSequenceMap.put(matcher.start(), specialSequence);
        }
    }

    private Font getFontFromSequence(Font previousFont, String specialSequence) {
        Font currentFont = new Font(previousFont);
        if ("<u>".equals(specialSequence) && !previousFont.isUnderlined()) {
            currentFont.setStyle(previousFont.getStyle() + Font.UNDERLINE);
        } else if ("</u>".equals(specialSequence) && previousFont.isUnderlined()) {
            currentFont.setStyle(previousFont.getStyle() - Font.UNDERLINE);
        } else if ("<i>".equals(specialSequence) && !previousFont.isItalic()) {
            currentFont.setStyle(previousFont.getStyle() + Font.ITALIC);
        } else if ("</i>".equals(specialSequence) && previousFont.isItalic()) {
            currentFont.setStyle(previousFont.getStyle() - Font.ITALIC);
        } else if ("<b>".equals(specialSequence) && !previousFont.isBold()) {
            currentFont.setStyle(previousFont.getStyle() + Font.BOLD);
        } else if ("</b>".equals(specialSequence) && previousFont.isBold()) {
            currentFont.setStyle(previousFont.getStyle() - Font.BOLD);
        } else if ("<s>".equals(specialSequence) && !previousFont.isStrikethru()) {
            currentFont.setStyle(previousFont.getStyle() + Font.STRIKETHRU);
        } else if ("</s>".equals(specialSequence) && previousFont.isStrikethru()) {
            currentFont.setStyle(previousFont.getStyle() - Font.STRIKETHRU);
        }
        return currentFont;
    }

}
