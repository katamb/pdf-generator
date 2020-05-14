package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.dto.NewTextBlockDto;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.dto.ValueTextComboDto;
import generate.pdf.openpdf.dto.ResponseWithMessageDto;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.enums.UpdateType;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import generate.pdf.openpdf.service.TemplateLanguageCreationService;
import generate.pdf.openpdf.service.TextUpdatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PdfEditingController {

    private final TemplateTextMapper templateTextMapper;
    private final TextUpdatingService textUpdatingService;
    private final TemplateLanguageCreationService templateLanguageCreationService;

    @GetMapping("all-templates")
    public List<String> getAllTemplates() {
        return templateTextMapper.findAllAvailableTemplates();
    }

    @GetMapping("all-enum-templates")
    public List<String> getAllEnumTemplates() {
        return Arrays.stream(TemplateCode.values()).map(TemplateCode::name).collect(Collectors.toList());
    }

    @GetMapping("all-languages")
    public List<ValueTextComboDto> getAllLanguages() {
        return templateTextMapper.findAllAvailableLanguages();
    }

    @GetMapping("template-languages/{templateCode}")
    public List<ValueTextComboDto> getTemplateLanguages(@PathVariable TemplateCode templateCode) {
        return templateTextMapper.findAllLanguagesForTemplate(templateCode);
    }

    @GetMapping("languages-by-code/{languageCode}")
    public ValueTextComboDto getLanguageName(@PathVariable LanguageCode languageCode) {
        return templateTextMapper.findLanguageByCode(languageCode);
    }

    @GetMapping("text-by-id/{templateCode}/{languageCode}/{id}")
    public TemplateTextDto findTextById(
            @PathVariable TemplateCode templateCode,
            @PathVariable LanguageCode languageCode,
            @PathVariable Long id
    ) {
        return templateTextMapper.findTextBlockById(templateCode, languageCode, id);
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @PutMapping("update-text/{updateType}")
    public ResponseWithMessageDto updateTextBlock(
            @PathVariable UpdateType updateType,
            @RequestBody @Valid TemplateTextDto updatedTextBlock
    ) {
        return textUpdatingService.update(updatedTextBlock, updateType);
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @PostMapping("add-language/{templateCode}/{oldLanguageCode}/{newLanguageCode}")
    public void createNewLanguageForTemplate(
            @PathVariable TemplateCode templateCode,
            @PathVariable LanguageCode oldLanguageCode,
            @PathVariable LanguageCode newLanguageCode
    ) {
        templateLanguageCreationService.createNewLanguageForTemplate(templateCode, oldLanguageCode, newLanguageCode);
    }

    @PreAuthorize("hasRole('ROLE_DEVELOPER')")
    @PostMapping("add-new-text-block")
    public void addNewTextBlock(@RequestBody @Valid NewTextBlockDto newTextBlockDto) {
        templateTextMapper.insertTextBlock(newTextBlockDto.getTextBlockValue());
        templateTextMapper.insertTemplateText(newTextBlockDto);
    }

}
