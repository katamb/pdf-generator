package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TextBlockService {

    private final TemplateTextMapper templateTextMapper;

    /**
     * Return static text for template in the form of a map for fast and easy access.
     */
    public Map<String, TemplateTextBlock> getTextsByTemplateAndLanguage(
            TemplateCode templateCode,
            LanguageCode languageCode
    ) {
        List<TemplateTextBlock> textBlocksWithStyle = templateTextMapper
                .getTextsByTemplateAndLanguage(templateCode.name(), languageCode.name());
        Map<String, TemplateTextBlock> map = new HashMap<>();
        for (TemplateTextBlock templateTextBlock : textBlocksWithStyle) {
            map.put(templateTextBlock.getTextBlockName(), templateTextBlock);
        }
        return map;
    }

    /**
     * Return static text for template in the form of a map for fast and easy access.
     */
    public List<TemplateTextBlock> getTextsByGroup(Map<String, TemplateTextBlock> textBlockMap, String groupName) {
        return textBlockMap.values()
                .stream()
                .filter(tb -> tb.getTextGroupCode().equals(groupName))
                .sorted(Comparator.comparing(TemplateTextBlock::getOrdering))
                .collect(Collectors.toList());
    }
}
