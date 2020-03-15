package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.dto.ValueTextCombo;
import generate.pdf.openpdf.dto.ResponseWithMessage;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.enums.UpdateType;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import generate.pdf.openpdf.service.TextUpdatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PdfEditingController {

    private final TemplateTextMapper templateTextMapper;
    private final TextUpdatingService textUpdatingService;

    @GetMapping("all-templates")
    public List<String> getAllTemplates() {
        return templateTextMapper.findAllAvailableTemplates();
    }

    @GetMapping("all-languages")
    public List<ValueTextCombo> getAllLanguages() {
        return templateTextMapper.findAllAvailableLanguages();
    }

    @GetMapping("template-languages/{templateCode}")
    public List<ValueTextCombo> getTemplateLanguages(@PathVariable TemplateCode templateCode) {
        return templateTextMapper.findAllLanguagesForTemplate(templateCode.toString());
    }

    @GetMapping("languages-by-code/{languageCode}")
    public ValueTextCombo getTemplateLanguages(@PathVariable LanguageCode languageCode) {
        return templateTextMapper.findLanguageByCode(languageCode.toString());
    }

    @GetMapping("text-by-id/{templateCode}/{languageCode}/{id}")
    public TemplateTextBlock pdfEditor(
            @PathVariable TemplateCode templateCode,
            @PathVariable LanguageCode languageCode,
            @PathVariable Long id
    ) {
        return templateTextMapper.findTextBlockById(templateCode.toString(), languageCode.toString(), id);
    }

    @PutMapping("update-text/{updateType}")
    public ResponseWithMessage updateTextBlock(
            @PathVariable UpdateType updateType,
            @RequestBody TemplateTextBlock updatedTextBlock
    ) {
        return textUpdatingService.update(updatedTextBlock, updateType);
    }

    @PostMapping("add-language/{templateCode}/{oldLanguageCode}/{newLanguageCode}")
    public void createNewLanguageForTemplate(
            @PathVariable TemplateCode templateCode,
            @PathVariable LanguageCode oldLanguageCode,
            @PathVariable LanguageCode newLanguageCode
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
