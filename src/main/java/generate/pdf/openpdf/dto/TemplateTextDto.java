package generate.pdf.openpdf.dto;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
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
public class TemplateTextDto {

    @NotNull
    private Long templateTextId;

    @NotNull
    private TemplateCode templateCode;

    @NotNull
    private LanguageCode languageCode;

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

    @NotNull
    @Min(0)
    private int paddingTop;

    @NotNull
    @Min(0)
    private int paddingBottom;

    @NotNull
    @Min(0)
    private int paddingLeft;

    @NotNull
    @Min(0)
    private int paddingRight;

    public void setTextBlockValue(String textBlockValue) {
        this.textBlockValue = textBlockValue;
        this.previousTextBlockValue = textBlockValue;
    }

    public void setPreviousTextBlockValue(String textBlockValue) {
        this.previousTextBlockValue = textBlockValue;
    }

    @PostConstruct
    public void assignPreviousTextBlockValue() {
        this.previousTextBlockValue = this.textBlockValue;
    }

    public static TemplateTextDto createNewBlockFromExistingWithSameStyles(
            TemplateTextDto templateTextDto,
            String text
    ) {
        TemplateTextDto blockWithStyle = new TemplateTextDto();
        blockWithStyle.setTextSize(templateTextDto.getTextSize());
        blockWithStyle.setHorizontalAlignment(templateTextDto.getHorizontalAlignment());
        blockWithStyle.setVerticalAlignment(templateTextDto.getVerticalAlignment());
        blockWithStyle.setTextBlockValue(text);
        blockWithStyle.setPaddingTop(templateTextDto.getPaddingTop());
        blockWithStyle.setPaddingBottom(templateTextDto.getPaddingBottom());
        blockWithStyle.setPaddingLeft(templateTextDto.getPaddingLeft());
        blockWithStyle.setPaddingRight(templateTextDto.getPaddingRight());
        return blockWithStyle;
    }

}
