package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.ResponseWithMessage;
import generate.pdf.openpdf.dto.TemplateTextBlock;
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
    public ResponseWithMessage update(TemplateTextBlock updatedTextBlock, UpdateType updateType) {
        Long textBlockUsagesAmount = templateTextMapper.findAmountOfTextBlockUsages(updatedTextBlock.getTextBlockId());
        String textBlockValue = templateTextMapper.findTextBlockValueById(updatedTextBlock.getTextBlockId());
        boolean isTextBlockValueUpdated = !textBlockValue.equals(updatedTextBlock.getTextBlockValue());
        boolean isTextBlockUsedMoreThanOnce = textBlockUsagesAmount > 1;
        boolean isConfirmationNeeded = UpdateType.CONFIRM_UPDATE.equals(updateType);

        if (isTextBlockValueUpdated && isTextBlockUsedMoreThanOnce && isConfirmationNeeded) {
            return multipleChoices(String.format("This text block is used by %s templates.", textBlockUsagesAmount));
        }

        templateTextMapper.insertTextBlock(updatedTextBlock);

        if (UpdateType.UPDATE_ALL.equals(updateType)) {
            templateTextMapper.updateAllTemplatesWithGivenText(updatedTextBlock);
        } else {
            templateTextMapper.updateTemplateToTextTranslation(updatedTextBlock);
        }

        return success("Template updated successfully!");
    }

    private ResponseWithMessage multipleChoices(String reason) {
        return new ResponseWithMessage(HttpStatus.MULTIPLE_CHOICES.value(), reason);
    }

    private ResponseWithMessage success(String reason) {
        return new ResponseWithMessage(HttpStatus.OK.value(), reason);
    }

}
