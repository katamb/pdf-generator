package generate.pdf.openpdf.template.loan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoanContractInputDto {

    private String loanTypeCode;
    private Borrower borrower;
    private Lender lender;
    private List<ScheduleLines> schedule;
    private ContractDetails loan;

}
