package generate.pdf.openpdf.template.loan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ScheduleLine {

    private String paymentDate;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal administrationFee;
    private BigDecimal payment;

}
