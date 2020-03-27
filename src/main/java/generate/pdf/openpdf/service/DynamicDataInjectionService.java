package generate.pdf.openpdf.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DynamicDataInjectionService {

    public String injectGivenValue(String baseText, String replacementValue) {
        return valueInjector(baseText, (String replaceableText) -> replacementValue);
    }

    public String injectValues(String baseText, Map replacementValues) {
        return valueInjector(baseText,
                (String replaceableText) -> getValueFromMapOrPlaceholder(replacementValues, replaceableText));
    }

    private String getValueFromMapOrPlaceholder(Map dynamicData, String replaceableText) {
        String[] mapPathToValue = replaceableText.substring(2, replaceableText.length() - 1).split("\\.");
        LinkedList<String> pathValuesAsList = new LinkedList<>(Arrays.asList(mapPathToValue));
        return findValueFromMap(pathValuesAsList, dynamicData);
    }

    private String valueInjector(String baseText, UnaryOperator<String> replacementValueProvidingOperator) {
        // Match pattern ${x}, where x is any character except whitespace
        Pattern pattern = Pattern.compile("\\$\\{[^\\s]*}");
        Matcher matcher = pattern.matcher(baseText);
        // Text can't be directly changed in loop
        String staticTextCopy = baseText;
        while (matcher.find()) {
            String replaceableText = baseText.substring(matcher.start(), matcher.end());
            String replacementText = replacementValueProvidingOperator.apply(replaceableText);
            staticTextCopy = staticTextCopy.replace(replaceableText, replacementText);
        }
        return staticTextCopy;
    }

    private String findValueFromMap(LinkedList<String> valueLocationInMap, Map dynamicDataMap) {
        String currentKey = valueLocationInMap.removeFirst();
        if (!dynamicDataMap.containsKey(currentKey)) {
            return "-";
        }
        if (valueLocationInMap.isEmpty()) {
            return dynamicDataMap.get(currentKey).toString();
        }
        return findValueFromMap(valueLocationInMap, (Map) dynamicDataMap.get(currentKey));
    }

}
