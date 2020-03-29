package generate.pdf.openpdf.dto;

import lombok.Data;

@Data
public class FileResponse {

    private String fileName;
    private byte[] file;

}
