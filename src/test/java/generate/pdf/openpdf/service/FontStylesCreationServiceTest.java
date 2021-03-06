package generate.pdf.openpdf.service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FontStylesCreationServiceTest {

    @InjectMocks
    private FontStylesCreationService fontStylesCreationService;
    private Font font;

    @BeforeEach
    void initUseCase() {
        font = new Font(Font.HELVETICA);
    }

    @Test
    void givenFont_whenCreatingStyles_thenFontWontGetMutated() {
        font.setStyle(Font.NORMAL);
        fontStylesCreationService.createPhraseWithInlineStyles(font, "<b>Hello!");

        assertEquals(Font.NORMAL, font.getStyle());
    }

    @Test
    void givenFontAndPhrase_whenCreatingBoldStyle_thenStyleGetsApplied() {
        font.setStyle(Font.NORMAL);
        Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, "<b>Hello</b>!");

        assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 0));
        assertEquals(Font.BOLD, getChunkStyleByIndex(phrase, 1));
        assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 2));
    }

    @Test
    void givenFontAndPhrase_whenCreatingItalicStyle_thenStyleGetsApplied() {
        font.setStyle(Font.NORMAL);
        Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, "<i>Hello</i>!");

        assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 0));
        assertEquals(Font.ITALIC, getChunkStyleByIndex(phrase, 1));
        assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 2));
    }

    @Test
    void givenFontAndPhrase_whenCreatingUnderlinedStyle_thenStyleGetsApplied() {
        font.setStyle(Font.NORMAL);
        Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, "<u>Hello</u>!");

        assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 0));
        assertEquals(Font.UNDERLINE, getChunkStyleByIndex(phrase, 1));
        assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 2));
    }

	@Test
	void givenFontAndPhrase_whenCreatingStrikedThroughStyle_thenStyleGetsApplied() {
		font.setStyle(Font.NORMAL);
		Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, "<s>Hello</s>!");

		assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 0));
		assertEquals(Font.STRIKETHRU, getChunkStyleByIndex(phrase, 1));
		assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 2));
	}

    @Test
    void givenFontAndPhrase_whenCreatingBoldUnderlinedStyle_thenStyleGetsApplied() {
        font.setStyle(Font.NORMAL);
        Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, "<u><b>Hello</u>!");

        assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 0));
        assertEquals(Font.UNDERLINE, getChunkStyleByIndex(phrase, 1));
        assertEquals((Font.UNDERLINE + Font.BOLD), getChunkStyleByIndex(phrase, 2));
        assertEquals(Font.BOLD, getChunkStyleByIndex(phrase, 3));
    }

	@Test
	void givenFontAndPhrase_whenCreatingDoubleBoldStyle_thenStyleGetsAppliedOnce() {
		font.setStyle(Font.NORMAL);
		Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, "<b><b>Hello</b>!");

		assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 0));
		assertEquals(Font.BOLD, getChunkStyleByIndex(phrase, 1));
		assertEquals(Font.BOLD, getChunkStyleByIndex(phrase, 2));
		assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 3));
	}

	@Test
	void givenFontAndPhrase_whenCreatingBoldStyleWithDoubledEndTag_thenUnnecessaryTagGetsIgnored() {
		font.setStyle(Font.NORMAL);
		Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, "<b>Hello</b></b>!");

		assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 0));
		assertEquals(Font.BOLD, getChunkStyleByIndex(phrase, 1));
		assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 2));
		assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 3));
	}

	@Test
	void givenFontAndPhrase_whenCreatingPhraseWithAllStyles_thenAllStylesGetApplied() {
		font.setStyle(Font.NORMAL);
		Phrase phrase = fontStylesCreationService.createPhraseWithInlineStyles(font, "<u><b><s><i>Hello</u>!");

		assertEquals(Font.NORMAL, getChunkStyleByIndex(phrase, 0));
		assertEquals(Font.UNDERLINE, getChunkStyleByIndex(phrase, 1));
		assertEquals((Font.UNDERLINE + Font.BOLD), getChunkStyleByIndex(phrase, 2));
		assertEquals((Font.UNDERLINE + Font.BOLD + Font.STRIKETHRU), getChunkStyleByIndex(phrase, 3));
		assertEquals((Font.UNDERLINE + Font.BOLD + Font.STRIKETHRU + Font.ITALIC), getChunkStyleByIndex(phrase, 4));
		assertEquals((Font.BOLD + Font.STRIKETHRU + Font.ITALIC), getChunkStyleByIndex(phrase, 5));
	}

	private int getChunkStyleByIndex(Phrase phrase, int index) {
		return ((Chunk) phrase.getChunks().get(index)).getFont().getStyle();
	}

}
