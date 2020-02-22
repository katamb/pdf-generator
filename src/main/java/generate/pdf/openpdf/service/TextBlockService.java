package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.mapper.TextBlockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TextBlockService {

    private final TextBlockMapper textBlockMapper;

    public Map<String, TextBlockWithStyle> getTextsByGroupAndLanguage(
            TemplateCode templateCode,
            LanguageCode languageCode
    ) {
        List<TextBlockWithStyle> textBlocksWithStyle = textBlockMapper
                .getTextsByGroupAndLanguage(templateCode.toString(), languageCode.toString());
        Map<String, TextBlockWithStyle> map = new HashMap<>();
        for (TextBlockWithStyle textBlockWithStyle : textBlocksWithStyle) {
            map.put(textBlockWithStyle.getTextBlockName(), textBlockWithStyle);
        }
        return map;
    }
}
