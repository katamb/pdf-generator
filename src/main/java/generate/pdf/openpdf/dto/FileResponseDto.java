package generate.pdf.openpdf.dto;

import lombok.Data;

@Data
public class FileResponseDto {

    private String fileName;
    private byte[] file;

}
