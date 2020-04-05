package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateLanguageCreationService {

    private final TemplateTextMapper templateTextMapper;

    public void createNewLanguageForTemplate(
            TemplateCode templateCode,
            LanguageCode oldLanguageCode,
            LanguageCode newLanguageCode
    ) {
        List<TemplateTextBlock> templateTextRows = templateTextMapper
                .getTextsByTemplateAndLanguage(templateCode.name(), oldLanguageCode.name());
        validateAddingNewLanguage(templateTextRows, templateCode, oldLanguageCode, newLanguageCode);

        for (TemplateTextBlock textBlock : templateTextRows) {
            textBlock.setLanguageCode(newLanguageCode.name());
        }
        templateTextMapper.batchInsert(templateTextRows);
    }

    private void validateAddingNewLanguage(
            List<TemplateTextBlock> templateTextRows,
            TemplateCode templateCode,
            LanguageCode oldLanguageCode,
            LanguageCode newLanguageCode
    ) {
        if (oldLanguageCode == newLanguageCode) {
            throw new BadRequestException("Language codes shouldn't match!");
        }
        if (templateTextRows.isEmpty()) {
            throw new BadRequestException("The language to base the new language on, doesn't exist!");
        }
        List<TemplateTextBlock> templateTextRowForNew = templateTextMapper
                .getTextsByTemplateAndLanguage(templateCode.name(), newLanguageCode.name());
        if (!templateTextRowForNew.isEmpty()) {
            throw new BadRequestException("This language already exists!");
        }
    }

}
