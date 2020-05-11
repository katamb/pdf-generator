package generate.pdf.openpdf.dto;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.enums.TemplateCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewTextBlockDto {

    @NotNull
    private TemplateCode templateCode;

    @NotNull
    private LanguageCode languageCode;

    @NotNull
    @Length(min = 0, max = 255)
    private String textGroupCode;

    @NotNull
    @Length(min = 0, max = 255)
    private String textBlockName;

    @NotNull
    private String textBlockValue;

    @Min(0)
    private Integer ordering;

    @NotNull
    private boolean numbering;

    @Min(0)
    private Integer numberingLevel;

}
