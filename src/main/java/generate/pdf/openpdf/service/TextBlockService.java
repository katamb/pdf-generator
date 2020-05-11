package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.TemplateTextDto;
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
    public Map<String, TemplateTextDto> getTextsByTemplateAndLanguage(
            TemplateCode templateCode,
            LanguageCode languageCode
    ) {
        List<TemplateTextDto> textBlocksWithStyle = templateTextMapper
                .getTextsByTemplateAndLanguage(templateCode, languageCode);
        Map<String, TemplateTextDto> map = new HashMap<>();
        for (TemplateTextDto templateTextDto : textBlocksWithStyle) {
            map.put(templateTextDto.getTextBlockName(), templateTextDto);
        }
        return map;
    }

    /**
     * Return static text for template in the form of a map for fast and easy access.
     */
    public List<TemplateTextDto> getTextsByGroup(Map<String, TemplateTextDto> textBlockMap, String groupName) {
        return textBlockMap.values()
                .stream()
                .filter(tb -> tb.getTextGroupCode().equals(groupName))
                .sorted(Comparator.comparing(TemplateTextDto::getOrdering))
                .collect(Collectors.toList());
    }
}
