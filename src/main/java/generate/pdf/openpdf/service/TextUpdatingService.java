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
     * If more than one match, let user choose, if all occurrences need to be updated or only current.
     * @param updatedTextBlock - Text block with updated parameters.
     * @param updateType - Holds state (update only this or all, if text block is used in many places)
     * @return response
     */
    public ResponseWithMessage update(TemplateTextBlock updatedTextBlock, UpdateType updateType) {
        Long textBlockUsagesAmount = templateTextMapper.findAmountOfTextBlockUsages(updatedTextBlock.getTextBlockId());
        boolean isTextBlockValueUpdated = !templateTextMapper.findTextBlockValueById(updatedTextBlock.getTextBlockId())
                .equals(updatedTextBlock.getTextBlockValue());

        if (isTextBlockValueUpdated && textBlockUsagesAmount > 1 && UpdateType.CONFIRM_UPDATE.equals(updateType)) {
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

    /**
     * If text block with the exact value already exists, use that, else insert new text block and use that.
     * @param updatedTextBlock - Text block updated by user.
     */
    @Deprecated
    private void insertNewTextBlockIfNecessaryElseUseExisting(TemplateTextBlock updatedTextBlock) {
        Long existingTextBlockWithNeededValue = templateTextMapper
                .findTextBlockIdByValue(updatedTextBlock.getTextBlockValue());
        if (existingTextBlockWithNeededValue != null) {
            updatedTextBlock.setTextBlockId(existingTextBlockWithNeededValue);
        } else {
            templateTextMapper.insertTextBlock(updatedTextBlock);
        }
        updatedTextBlock.setTextBlockId(updatedTextBlock.getTextBlockId());
    }

    private ResponseWithMessage multipleChoices(String reason) {
        return new ResponseWithMessage(HttpStatus.MULTIPLE_CHOICES.value(), reason);
    }

    private ResponseWithMessage success(String reason) {
        return new ResponseWithMessage(HttpStatus.OK.value(), reason);
    }

}
