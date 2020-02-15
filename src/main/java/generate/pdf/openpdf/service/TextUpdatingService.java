package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.ResponseWithReason;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.enums.UpdateType;
import generate.pdf.openpdf.mapper.TextBlockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import static generate.pdf.openpdf.enums.UpdateType.*;

@Service
@RequiredArgsConstructor
public class TextUpdatingService {

    private final TextBlockMapper textBlockMapper;

    public ResponseWithReason update(TextBlockWithStyle updatedTextBlock, UpdateType updateType) {
        Long textBlockUsages = textBlockMapper.findAmountOfTextBlockUsages(updatedTextBlock.getTextBlockId());
        boolean isTextBlockValueUpdated = !textBlockMapper.findTextBlockValueById(updatedTextBlock.getTextBlockId())
                .equals(updatedTextBlock.getTextBlockValue());
        if (isTextBlockValueUpdated && textBlockUsages > 1 && CONFIRM_UPDATE.equals(updateType)) {
            return multipleChoices(String.format("This text block is used by %s templates.", textBlockUsages));
        }
        if (isTextBlockValueUpdated && UPDATE_ONLY_CURRENT.equals(updateType)) {
            textBlockMapper.insertTextBlock(updatedTextBlock);
            updatedTextBlock.setTextBlockId(textBlockMapper.findTextBlockIdByValue(updatedTextBlock.getTextBlockValue()));
        } else if (isTextBlockValueUpdated) {
            textBlockMapper.updateTextBlock(updatedTextBlock.getTextBlockValue(), updatedTextBlock.getTextBlockId());
        }
        textBlockMapper.updateTemplateToTextTranslation(updatedTextBlock);
        return success("Template updated successfully!");
    }

    @ResponseStatus(HttpStatus.MULTIPLE_CHOICES)
    private ResponseWithReason multipleChoices(String reason) {
        return new ResponseWithReason(reason);
    }

    @ResponseStatus(HttpStatus.OK)
    private ResponseWithReason success(String reason) {
        return new ResponseWithReason(reason);
    }
}
