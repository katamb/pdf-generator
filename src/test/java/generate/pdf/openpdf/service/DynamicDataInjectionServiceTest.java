package generate.pdf.openpdf.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DynamicDataInjectionServiceTest {

    @InjectMocks
    private DynamicDataInjectionService templateLanguageCreationService;

    @Test
    void testInjectGivenValueSimple() {
        String baseString = "Say my name! - You are ${pseudonym}.";
        String replacementValue = "Heisenberg";
        String expectedResult = "Say my name! - You are Heisenberg.";
        String actualResult = templateLanguageCreationService.injectGivenValue(baseString, replacementValue);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testInjectGivenValueMultipleValuesMissing() {
        String baseString = "Say my name! - You are ${pseudonym}. - Yes, I'm ${pseudonym}.";
        String replacementValue = "Heisenberg";
        String expectedResult = "Say my name! - You are Heisenberg. - Yes, I'm Heisenberg.";
        String actualResult = templateLanguageCreationService.injectGivenValue(baseString, replacementValue);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testInjectGivenValueNoValuesMissing() {
        String baseString = "Say my name! - You are Heisenberg.";
        String replacementValue = "Heisenberg";
        String expectedResult = "Say my name! - You are Heisenberg.";
        String actualResult = templateLanguageCreationService.injectGivenValue(baseString, replacementValue);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testInjectValuesSimple() {
        String baseString = "Say my name! - You are ${pseudonym}.";
        Map<String, Object> pseudonym = new HashMap<>();
        pseudonym.put("pseudonym", "Heisenberg");
        String expectedResult = "Say my name! - You are Heisenberg.";
        String actualResult = templateLanguageCreationService.injectValues(baseString, pseudonym);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testInjectValuesOneMissing() {
        String baseString = "Say my name! - You are ${pseudonym}. - I'm ${name}.";
        Map<String, Object> pseudonym = new HashMap<>();
        pseudonym.put("pseudonym", "Heisenberg");
        String expectedResult = "Say my name! - You are Heisenberg. - I'm -.";
        String actualResult = templateLanguageCreationService.injectValues(baseString, pseudonym);

        assertEquals(expectedResult, actualResult);
    }

	@Test
	void testInjectValuesNestedMap() {
		String baseString = "Say my name! - You are ${breakingBad.mainCharacter.pseudonym}.";
		Map<String, Object> pseudonym = new HashMap<>();
		pseudonym.put("pseudonym", "Heisenberg");
		Map<String, Object> mainCharacter = new HashMap<>();
		mainCharacter.put("mainCharacter", pseudonym);
		Map<String, Object> breakingBad = new HashMap<>();
		breakingBad.put("breakingBad", mainCharacter);
		String expectedResult = "Say my name! - You are Heisenberg.";
		String actualResult = templateLanguageCreationService.injectValues(baseString, breakingBad);

		assertEquals(expectedResult, actualResult);
	}

}
