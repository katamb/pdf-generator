package generate.pdf.openpdf.service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DynamicDataInjectionService {

    //    private String injector(String staticText, Map dynamicData) {
//        Pattern pattern = Pattern.compile("\\$\\{.+}");
//        Matcher matcher = pattern.matcher(staticText);
//        if (matcher.find()) {
//            System.out.println(matcher.group(1));
//        }
//    }

    private String injector(String staticText, Map dynamicData) {
        // Match pattern ${x}, where x is any english alphanumeric character, underscore, dot or minus
        Pattern pattern = Pattern.compile("\\$\\{[^;]*}");
        Matcher matcher = pattern.matcher(staticText);
        // Text can't be directly changed in loop
        String staticTextCopy = staticText;
        while (matcher.find()) {
            String replaceableText = staticText.substring(matcher.start(), matcher.end());
            String[] mapPathToValue = replaceableText.substring(2, replaceableText.length() - 1).split("\\.");
            LinkedList<String> ads = new LinkedList<>(Arrays.asList(mapPathToValue));
            staticTextCopy = staticTextCopy.replace(replaceableText, findMapValue(ads, dynamicData));
        }
        return staticTextCopy;
    }

    private String findMapValue(LinkedList<String> valueLocationInMap, Map dynamicDataMap) {
        if (valueLocationInMap.size() == 1) {
            return dynamicDataMap.get(valueLocationInMap.get(0)).toString();
        }
        String temp = valueLocationInMap.get(0);
        valueLocationInMap.removeFirst();
        return findMapValue(valueLocationInMap, (Map) dynamicDataMap.get(temp));
    }


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
