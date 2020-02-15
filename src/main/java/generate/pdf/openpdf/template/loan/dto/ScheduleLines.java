package generate.pdf.openpdf.template.loan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ScheduleLines {

    private BigDecimal principal;
    private BigDecimal interest;
    private LocalDate paymentDate;

}
