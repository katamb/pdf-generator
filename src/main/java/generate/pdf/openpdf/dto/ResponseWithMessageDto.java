package generate.pdf.openpdf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWithMessageDto {

    private Integer statusCode;
    private String message;

}
