package generate.pdf.openpdf.dto;

import lombok.Data;

@Data
public class TextBlockWithStyle {

    private long textBlockId;
    private String textBlockValue;
    private long templateToTextTranslationId;
    private String textBlockName;
    private float textSize;
    private int horizontalAlignment;
    private int verticalAlignment;

    public static TextBlockWithStyle createNewBlockFromExistingWithSameStyles(
            TextBlockWithStyle textBlockWithStyle,
            String text
    ) {
        TextBlockWithStyle blockWithStyle = new TextBlockWithStyle();
        blockWithStyle.setTextSize(textBlockWithStyle.getTextSize());
        blockWithStyle.setHorizontalAlignment(textBlockWithStyle.getHorizontalAlignment());
        blockWithStyle.setVerticalAlignment(textBlockWithStyle.getVerticalAlignment());
        blockWithStyle.setTextBlockValue(text);
        return blockWithStyle;
    }

}
