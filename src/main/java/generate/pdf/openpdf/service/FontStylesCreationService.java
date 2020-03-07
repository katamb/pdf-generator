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

    private HashMap<Integer, String> indexMap;

    public Phrase phraseWithBoldItalicUnderlineStyles(Font regularFont, String text) {
        indexMap = new HashMap<>();
        List<String> specialSequences = Arrays.asList("<u>", "</u>", "<i>", "</i>", "<b>", "</b>");

        for (String specialSequence : specialSequences) {
            addToMap(specialSequence, text);
        }
        List<Integer> indexes = indexMap.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        Integer offset = 0;
        Phrase phrase = new Phrase();
        Integer previousIndex = 0;
        Font previousFont;
        Font currentFont = new Font(regularFont);
        currentFont.setStyle(Font.NORMAL);

        for (Integer index : indexes) {
            String textPart = text.substring(previousIndex + offset, index);
            Chunk chunk = new Chunk(textPart, currentFont);
            phrase.add(chunk);
            previousIndex = index;
            if (indexMap.get(index).equals("<u>")) {
                previousFont = currentFont;
                currentFont = new Font(previousFont);
                currentFont.setStyle(previousFont.getStyle() + Font.UNDERLINE);
            } else if (indexMap.get(index).equals("</u>")) {
                previousFont = currentFont;
                currentFont = new Font(previousFont);
                currentFont.setStyle(previousFont.getStyle() - Font.UNDERLINE);
            } else if (indexMap.get(index).equals("<i>")) {
                previousFont = currentFont;
                currentFont = new Font(previousFont);
                currentFont.setStyle(previousFont.getStyle() + Font.ITALIC);
            } else if (indexMap.get(index).equals("</i>")) {
                previousFont = currentFont;
                currentFont = new Font(previousFont);
                currentFont.setStyle(previousFont.getStyle() - Font.ITALIC);
            } else if (indexMap.get(index).equals("<b>")) {
                previousFont = currentFont;
                currentFont = new Font(previousFont);
                currentFont.setStyle(previousFont.getStyle() + Font.BOLD);
            } else if (indexMap.get(index).equals("</b>")) {
                previousFont = currentFont;
                currentFont = new Font(previousFont);
                currentFont.setStyle(previousFont.getStyle() - Font.BOLD);
            }
            offset = indexMap.get(index).length();
        }
        String textPart = text.substring(previousIndex + offset);
        Chunk chunk = new Chunk(textPart, currentFont);
        phrase.add(chunk);

        return phrase;
    }

    private void addToMap(String specialSequence, String text) {
        Matcher matcher = Pattern.compile(specialSequence).matcher(text);
        while (matcher.find()) {
            indexMap.put(matcher.start(), specialSequence);
        }
    }

    /**
     * First version for markdown style
     */
    @Deprecated
    public Phrase getBoldStrings(Font font, String text) {
        Pattern pattern = Pattern.compile("\\*\\*[^;]*\\*\\*");
        Matcher matcher = pattern.matcher(text);
        // Text can't be directly changed in loop
        Phrase phrase = new Phrase();
        Font boldFont = new Font(font);
        boldFont.setStyle(Font.BOLD);
        phrase.setFont(font);

        int index = 0;
        while (matcher.find()) {
            String textPart = text.substring(index, matcher.start());
            Chunk chunk = new Chunk(textPart, font);
            phrase.add(chunk);
            textPart = text.substring(matcher.start() + 2, matcher.end() - 2);
            chunk = new Chunk(textPart, boldFont);
            phrase.add(chunk);
            index = matcher.end();
        }
        String lastTextPart = text.substring(index, text.length());
        Chunk chunk = new Chunk(lastTextPart, font);
        phrase.add(chunk);

        return phrase;
    }

}
