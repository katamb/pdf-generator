package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.TemplateTextDto;
import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.mapper.TemplateTextMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TextBlockServiceTest {

    @Mock
    private TemplateTextMapper templateTextMapper;
    @InjectMocks
    private TextBlockService textBlockService;

    @Test
    void givenHundredTextBlocks_whenAsked_thenAllGetReturned() {
        // Given
        List<TemplateTextDto> generatedTextBlocks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            TemplateTextDto textBlock = new TemplateTextDto();
            textBlock.setTextBlockValue(UUID.randomUUID().toString());
            textBlock.setTextBlockName("Name " + i);
            generatedTextBlocks.add(textBlock);
        }
        // When
        when(templateTextMapper.getTextsByTemplateAndLanguage(any(), any())).thenReturn(generatedTextBlocks);
        // Then
        Map<String, TemplateTextDto> valueMap =
                textBlockService.getTextsByTemplateAndLanguage(TemplateCode.PRIVATE_CAR_LOAN_CONTRACT_EE, LanguageCode.et);
        assertEquals(100, valueMap.keySet().size());
    }

    @Test
    void givenTextBlocksWithDifferentGroups_whenOneGroupQueried_thenOnlyThisGroupGetsReturned() {
        // Given
        Map<String, TemplateTextDto> generatedTextBlocks = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            TemplateTextDto textBlock = new TemplateTextDto();
            textBlock.setTextBlockValue(UUID.randomUUID().toString());
            textBlock.setTextBlockName("Name " + i);
            if (i >= 20 && i < 42) {
                textBlock.setTextGroupCode("Searched group");
                textBlock.setOrdering(i - 20);
            } else {
                textBlock.setTextGroupCode("Not a searched group");
            }
            generatedTextBlocks.put(textBlock.getTextBlockName(), textBlock);
        }
        // When
        List<TemplateTextDto> texts = textBlockService.getTextsByGroup(generatedTextBlocks, "Searched group");
        // Then
        assertEquals(22, texts.size());
        assertEquals(3, texts.get(3).getOrdering());
    }

}
