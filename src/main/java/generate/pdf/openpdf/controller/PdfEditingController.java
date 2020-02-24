package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.dto.ResponseWithReason;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.enums.UpdateType;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import generate.pdf.openpdf.service.TextUpdatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @CrossOrigin
    @GetMapping("text-by-id/{templateCode}/{languageCode}/{id}")
    public TextBlockWithStyle pdfEditor(
            @PathVariable TemplateCode templateCode,
            @PathVariable LanguageCode languageCode,
            @PathVariable Long id
    ) {
        return templateTextMapper.findTextBlockById(templateCode.toString(), languageCode.toString(), id);
    }

    @CrossOrigin
    @GetMapping("all-templates")
    public List<String> pdfEditor() {
        return templateTextMapper.findAllTemplates();
    }

    @CrossOrigin
    @GetMapping("template-languages/{templateCode}")
    public List<String> pdfEditor(@PathVariable TemplateCode templateCode) {
        return templateTextMapper.findAllLanguagesForTemplate(templateCode.toString());
    }

    @CrossOrigin
    @PutMapping("update-text/{updateType}")
    public ResponseWithReason updateTextBlock(
            @PathVariable UpdateType updateType,
            @RequestBody TextBlockWithStyle updatedTextBlock
    ) {
        return textUpdatingService.update(updatedTextBlock, updateType);
    }
}
