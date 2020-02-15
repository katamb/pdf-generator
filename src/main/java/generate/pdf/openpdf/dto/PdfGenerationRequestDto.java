package generate.pdf.openpdf.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class PdfGenerationRequestDto {

    @NotNull
    private String requestedTemplate;
    @NotNull
    private String requestedTemplateLanguage;
    @NotNull
    private String json;

}
