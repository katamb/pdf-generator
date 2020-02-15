package generate.pdf.openpdf.template.loan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoanContractInputDto {

    private String loanTypeCode;
    private Borrower borrower = new Borrower();
    private Lender lender = new Lender();
    private List<ScheduleLines> schedule;
    private ContractDetails loan;

}
