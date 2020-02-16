package generate.pdf.openpdf.template.loan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Borrower {

    private String name;
    private String address;
    private String phone;
    private String iban;

}
