package generate.pdf.openpdf.template.loan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ScheduleYear {

    private String year;
    private List<ScheduleLine> scheduleLines;

}
