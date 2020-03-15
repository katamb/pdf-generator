package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateLanguageCreationService {

    private final TemplateTextMapper templateTextMapper;

    public void createNewLanguageForTemplate(
            TemplateCode templateCode,
            LanguageCode oldLanguageCode,
            LanguageCode newLanguageCode
    ) {
        if (oldLanguageCode == newLanguageCode) {
            throw new BadRequestException("This template already exists in this language!");
        }

        List<TemplateTextBlock> templateTextRows = templateTextMapper
                .getTextsByTemplateAndLanguage(templateCode.toString(), oldLanguageCode.toString());
        for (TemplateTextBlock textBlock : templateTextRows) {
            textBlock.setLanguageCode(newLanguageCode.toString());
        }
        templateTextMapper.batchInsert(templateTextRows);
    }

}
