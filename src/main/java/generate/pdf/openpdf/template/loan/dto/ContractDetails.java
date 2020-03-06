package generate.pdf.openpdf.template.loan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private String signingDate;
    private String transferDate;
    private String lastInstallment;
    private String currency;
    private String contractNumber;

}
