package generate.pdf.openpdf.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserSqlFileDto {

    private Long id;
    private String username;
    private String sqlFileName;
    private boolean selected;
    private LocalDateTime createdAt;

}
