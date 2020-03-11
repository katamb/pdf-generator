package generate.pdf.openpdf.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class TemplateTextBlock {

    private Long templateTextId;
    private String templateCode;
//    @Length(min = 2, max = 2)
    private String languageCode;
    private String textGroupCode;
    private Integer ordering;
    private boolean numbering;
    private Integer numberingLevel;
    private String textBlockName;
    private Long textBlockId;
    @Setter(AccessLevel.NONE)
    private String textBlockValue;
    @Setter(AccessLevel.NONE)
    private String previousTextBlockValue;
    private float textSize;
    private int horizontalAlignment;
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
