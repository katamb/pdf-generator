package generate.pdf.openpdf.service;

import generate.pdf.openpdf.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DynamicDataInjectionService {

    public String injectValue(String currentText, String newString) {
        // Match pattern ${x}, where x is any character except whitespace
        Pattern pattern = Pattern.compile("\\$\\{[^\\s]*}");
        Matcher matcher = pattern.matcher(currentText);
        // Text can't be directly changed in loop
        String currentTextCopy = currentText;
        while (matcher.find()) {
            String replaceableText = currentText.substring(matcher.start(), matcher.end());
            currentTextCopy = currentTextCopy.replace(replaceableText, newString);
        }
        return currentTextCopy;
    }

    public String injectValues(String staticText, Map dynamicData) {
        // Match pattern ${x}, where x is any character except whitespace
        Pattern pattern = Pattern.compile("\\$\\{[^\\s]*}");
        Matcher matcher = pattern.matcher(staticText);
        // Text can't be directly changed in loop
        String staticTextCopy = staticText;
        while (matcher.find()) {
            String replaceableText = staticText.substring(matcher.start(), matcher.end());
            String[] mapPathToValue = replaceableText.substring(2, replaceableText.length() - 1).split("\\.");
            LinkedList<String> ads = new LinkedList<>(Arrays.asList(mapPathToValue));
            staticTextCopy = staticTextCopy.replace(replaceableText, findMapValue(ads, dynamicData, replaceableText));
        }
        return staticTextCopy;
    }

    private String findMapValue(LinkedList<String> valueLocationInMap, Map dynamicDataMap, String replaceableText) {
        try {
            if (valueLocationInMap.size() == 1) {
                return dynamicDataMap.get(valueLocationInMap.get(0)).toString();
            }
            String temp = valueLocationInMap.get(0);
            valueLocationInMap.removeFirst();
            return findMapValue(valueLocationInMap, (Map) dynamicDataMap.get(temp), replaceableText);
        } catch (NullPointerException e) {
            String message = String.format("Missing value in input: %s.", replaceableText);
            throw new BadRequestException(message);
        }
    }

}
