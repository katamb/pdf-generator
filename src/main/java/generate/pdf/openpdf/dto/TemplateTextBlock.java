package generate.pdf.openpdf.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TemplateTextBlock {

    @NotNull
    private Long templateTextId;

    @NotNull
    @Length(min = 1, max = 150)
    private String templateCode;

    @NotNull
    @Length(min = 2, max = 2)
    private String languageCode;

    @NotNull
    @Length(min = 0, max = 255)
    private String textGroupCode;

    private Integer ordering;

    private boolean numbering;

    private Integer numberingLevel;

    @NotNull
    @Length(min = 0, max = 255)
    private String textBlockName;

    @NotNull
    private Long textBlockId;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String textBlockValue;

    @NotNull
    @Setter(AccessLevel.NONE)
    private String previousTextBlockValue;

    @NotNull
    @Min(1)
    private float textSize;

    /**
     * According to values defined in OpenPdf library Element interface.
     * -1 -> LEFT alignment
     * 0 -> LEFT alignment (DEFAULT)
     * 1 -> CENTER alignment
     * 2 -> RIGHT alignment
     * 3 -> JUSTIFIED alignment
     */
    @NotNull
    @Min(-1)
    @Max(3)
    private int horizontalAlignment;

    /**
     * According to values defined in OpenPdf library Element interface.
     * 4 -> TOP alignment (DEFAULT)
     * 5 -> CENTER alignment
     * 6 -> BOTTOM alignment
     * 7 -> BASELINE alignment
     * 8 -> JUSTIFIED alignment
     */
    @NotNull
    @Min(4)
    @Max(8)
    private int verticalAlignment;

    public void setTextBlockValue(String textBlockValue) {
        this.textBlockValue = textBlockValue;
        this.previousTextBlockValue = textBlockValue;
    }

    @PostConstruct
    public void setPreviousTextBlockValue() {
        this.previousTextBlockValue = this.textBlockValue;
    }

    public static TemplateTextBlock createNewBlockFromExistingWithSameStyles(
            TemplateTextBlock templateTextBlock,
            String text
    ) {
        TemplateTextBlock blockWithStyle = new TemplateTextBlock();
        blockWithStyle.setTextSize(templateTextBlock.getTextSize());
        blockWithStyle.setHorizontalAlignment(templateTextBlock.getHorizontalAlignment());
        blockWithStyle.setVerticalAlignment(templateTextBlock.getVerticalAlignment());
        blockWithStyle.setTextBlockValue(text);
        return blockWithStyle;
    }

}
