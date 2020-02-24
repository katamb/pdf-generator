package generate.pdf.openpdf.template.loan.dto;

import generate.pdf.openpdf.dto.InputData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoanContractInputDto extends InputData {

    private String loanTypeCode;
    private Borrower borrower;
    private Lender lender;
    private List<ScheduleYear> scheduleYears;
    private ScheduleLine scheduleSummary;
    private ContractDetails loan;

}
