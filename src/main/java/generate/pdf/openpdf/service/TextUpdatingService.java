package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.ResponseWithReason;
import generate.pdf.openpdf.dto.TextBlockWithStyle;
import generate.pdf.openpdf.enums.UpdateType;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static generate.pdf.openpdf.enums.UpdateType.CONFIRM_UPDATE;
import static generate.pdf.openpdf.enums.UpdateType.UPDATE_ONLY_CURRENT;

@Service
@RequiredArgsConstructor
public class TextUpdatingService {

    private final TemplateTextMapper templateTextMapper;

    /**
     * Update text or styling.
     * If more than one match, let user choose, if all occurrences need to be updated or only current.
     * @param updatedTextBlock - Text block with updated parameters.
     * @param updateType - Holds state (update only this or all, if text block is used in many places)
     * @return response
     */
    public ResponseWithReason update(TextBlockWithStyle updatedTextBlock, UpdateType updateType) {
        Long textBlockUsagesAmount = templateTextMapper.findAmountOfTextBlockUsages(updatedTextBlock.getTextBlockId());
        boolean isTextBlockValueUpdated = !templateTextMapper.findTextBlockValueById(updatedTextBlock.getTextBlockId())
                .equals(updatedTextBlock.getTextBlockValue());

        if (isTextBlockValueUpdated && textBlockUsagesAmount > 1 && CONFIRM_UPDATE.equals(updateType)) {
            return multipleChoices(String.format("This text block is used by %s templates.", textBlockUsagesAmount));
        }

        if (isTextBlockValueUpdated && UPDATE_ONLY_CURRENT.equals(updateType)) {
            insertNewTextBlockIfNecessaryElseUseExisting(updatedTextBlock);
        } else if (isTextBlockValueUpdated) {
            updateTextBlockIfNeededElseUseExisting(updatedTextBlock);
        }

        templateTextMapper.updateTemplateToTextTranslation(updatedTextBlock);
        return success("Template updated successfully!");
    }

    /**
     * If text block with the exact value already exists, use that, else insert new text block and use that.
     * @param updatedTextBlock - Text block updated by user.
     */
    private void updateTextBlockIfNeededElseUseExisting(TextBlockWithStyle updatedTextBlock) {
        Long existingTextBlockWithNeededValue = templateTextMapper
                .findTextBlockIdByValue(updatedTextBlock.getTextBlockValue());
        if (existingTextBlockWithNeededValue != null) {
            updatedTextBlock.setTextBlockId(existingTextBlockWithNeededValue);
        } else {
            templateTextMapper.updateTextBlock(
                    updatedTextBlock.getTextBlockValue(), updatedTextBlock.getTextBlockId());
        }
    }

    /**
     * If text block with the exact value already exists, use that, else insert new text block and use that.
     * @param updatedTextBlock - Text block updated by user.
     */
    private void insertNewTextBlockIfNecessaryElseUseExisting(TextBlockWithStyle updatedTextBlock) {
        Long existingTextBlockWithNeededValue = templateTextMapper
                .findTextBlockIdByValue(updatedTextBlock.getTextBlockValue());
        if (existingTextBlockWithNeededValue != null) {
            updatedTextBlock.setTextBlockId(existingTextBlockWithNeededValue);
        } else {
            templateTextMapper.insertTextBlock(updatedTextBlock);
        }
        updatedTextBlock.setTextBlockId(updatedTextBlock.getTextBlockId());
    }

    private ResponseWithReason multipleChoices(String reason) {
        return new ResponseWithReason(HttpStatus.MULTIPLE_CHOICES.value(), reason);
    }

    private ResponseWithReason success(String reason) {
        return new ResponseWithReason(HttpStatus.OK.value(), reason);
    }
}
