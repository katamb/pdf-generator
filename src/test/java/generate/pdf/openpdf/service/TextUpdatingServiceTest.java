package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.ResponseWithMessageDto;
import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static generate.pdf.openpdf.enums.UpdateType.CONFIRM_UPDATE;
import static generate.pdf.openpdf.enums.UpdateType.UPDATE_ALL;
import static generate.pdf.openpdf.enums.UpdateType.UPDATE_ONLY_CURRENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TextUpdatingServiceTest {

    @Mock
    private TemplateTextMapper templateTextMapper;
    @InjectMocks
    private TextUpdatingService textUpdatingService;

    @Test
    void givenTextBlock_whenBlockHasMultipleUsesAndValueNotChangedAndUpdateTypeNotChosen_thenChangeSuccessfullyCreated() {
        // Given
        Long textBlockId = 4L;
        TemplateTextDto textBlock = new TemplateTextDto();
        textBlock.setTextBlockId(textBlockId);
        textBlock.setTextBlockValue("Testvalue!");
        // When
        when(templateTextMapper.findAmountOfTextBlockUsages(textBlockId)).thenReturn(2L);
        when(templateTextMapper.findTextBlockValueById(textBlockId)).thenReturn("Testvalue!");
        // Then
        ResponseWithMessageDto response = textUpdatingService.update(textBlock, CONFIRM_UPDATE);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        verify(templateTextMapper, times(1)).updateTemplateToTextTranslation(any());
    }

    @Test
    void givenTextBlock_whenBlockHasMultipleUsesAndValueChangedAndUpdateTypeNotChosen_thenReturnMultipleChoice() {
        // Given
        Long textBlockId = 4L;
        TemplateTextDto textBlock = new TemplateTextDto();
        textBlock.setTextBlockId(textBlockId);
        textBlock.setTextBlockValue("Testvalue!");
        // When
        when(templateTextMapper.findAmountOfTextBlockUsages(textBlockId)).thenReturn(2L);
        when(templateTextMapper.findTextBlockValueById(textBlockId)).thenReturn("Not a testvalue!");
        // Then
        ResponseWithMessageDto response = textUpdatingService.update(textBlock, CONFIRM_UPDATE);
        assertEquals(HttpStatus.MULTIPLE_CHOICES.value(), response.getStatusCode());
        verify(templateTextMapper, times(0)).updateTemplateToTextTranslation(any());
    }

    @Test
    void givenTextBlock_whenBlockHasMultipleUsesAndValueChangedAndUpdateTypeOnlyCurrent_thenNewBlockAdded() {
        // Given
        Long textBlockId = 4L;
        TemplateTextDto textBlock = new TemplateTextDto();
        textBlock.setTextBlockId(textBlockId);
        textBlock.setTextBlockValue("Testvalue!");
        // When
        when(templateTextMapper.findAmountOfTextBlockUsages(textBlockId)).thenReturn(2L);
        when(templateTextMapper.findTextBlockValueById(textBlockId)).thenReturn("Not a testvalue!");
        // Then
        ResponseWithMessageDto response = textUpdatingService.update(textBlock, UPDATE_ONLY_CURRENT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        verify(templateTextMapper, times(1)).insertTextBlock(any());
        verify(templateTextMapper, times(1)).updateTemplateToTextTranslation(any());
    }

    @Test
    void givenTextBlock_whenBlockHasMultipleUsesAndValueChangedAndUpdateTypeAll_thenAllBlocksUpdated() {
        // Given
        Long textBlockId = 4L;
        TemplateTextDto textBlock = new TemplateTextDto();
        textBlock.setTextBlockId(textBlockId);
        textBlock.setTextBlockValue("Testvalue!");
        // When
        when(templateTextMapper.findAmountOfTextBlockUsages(textBlockId)).thenReturn(2L);
        when(templateTextMapper.findTextBlockValueById(textBlockId)).thenReturn("Not a testvalue!");
        // Then
        ResponseWithMessageDto response = textUpdatingService.update(textBlock, UPDATE_ALL);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        verify(templateTextMapper, times(1)).insertTextBlock(any());
        verify(templateTextMapper, times(1)).updateAllTemplatesWithGivenText(any());
        verify(templateTextMapper, times(0)).updateTemplateToTextTranslation(any());
    }

}
