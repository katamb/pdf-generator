package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.ResponseWithMessageDto;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.enums.UpdateType;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TextUpdatingService {

    private final TemplateTextMapper templateTextMapper;

    /**
     * Update text or styling.
     * If more than one template uses this text, let user choose, if all occurrences need to be updated or only current.
     * @param updatedTextBlock - Text block with updated parameters.
     * @param updateType - Holds state (update only current block or all blocks)
     * @return response
     */
    public ResponseWithMessageDto update(TemplateTextDto updatedTextBlock, UpdateType updateType) {
        Long textBlockUsagesAmount = templateTextMapper.findAmountOfTextBlockUsages(updatedTextBlock.getTextBlockId());
        String textBlockValue = templateTextMapper.findTextBlockValueById(updatedTextBlock.getTextBlockId());
        boolean isTextBlockValueUpdated = !textBlockValue.equals(updatedTextBlock.getTextBlockValue());
        boolean isTextBlockUsedMoreThanOnce = textBlockUsagesAmount > 1;
        boolean isConfirmationNeeded = UpdateType.CONFIRM_UPDATE.equals(updateType);

        if (isTextBlockValueUpdated && isTextBlockUsedMoreThanOnce && isConfirmationNeeded) {
            return multipleChoices(String.format("This text block is used by %s templates.", textBlockUsagesAmount));
        }

        templateTextMapper.insertTextBlock(updatedTextBlock.getTextBlockValue());

        if (UpdateType.UPDATE_ALL.equals(updateType)) {
            templateTextMapper.updateAllTemplatesWithGivenText(updatedTextBlock);
        } else {
            templateTextMapper.updateTemplateToTextTranslation(updatedTextBlock);
        }

        return success("Template updated successfully!");
    }

    private ResponseWithMessageDto multipleChoices(String reason) {
        return new ResponseWithMessageDto(HttpStatus.MULTIPLE_CHOICES.value(), reason);
    }

    private ResponseWithMessageDto success(String reason) {
        return new ResponseWithMessageDto(HttpStatus.OK.value(), reason);
    }

}
