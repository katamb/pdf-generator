package generate.pdf.openpdf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWithReason {

    private Integer statusCode;
    private String reason;

}
