package generate.pdf.openpdf.template.loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ContractDetails {

    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private BigDecimal annualPercentageRate;
    private BigDecimal conclusionFee;
    private BigDecimal penaltyRate;
    private int loanPeriod;
    private int fullRepaymentDaysAdvanceNotification;
    private int maxAllowedDaysAfterNotification;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // todo date stuff aint working properly
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
    private String signingDate;
    private String transferDate;
    private String lastInstallment;
    private String currency;

}
