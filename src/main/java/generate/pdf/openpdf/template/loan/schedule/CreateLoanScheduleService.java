package generate.pdf.openpdf.template.loan.schedule;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.service.table.CreateCellService;
import generate.pdf.openpdf.template.loan.dto.LoanContractInputDto;
import generate.pdf.openpdf.template.loan.dto.ScheduleLine;
import generate.pdf.openpdf.template.loan.dto.ScheduleYear;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateLoanScheduleService {

    private static final int MONTHS_IN_YEAR = 12;
    private static final int DEFAULT_MIN_HEIGHT = 14;
    private final CreateCellService createCellService;

    private Color backgroundColor;
    private int paymentMonthCounter;
    private LoanContractInputDto loanContractInputDto;
    private Map<String, TemplateTextBlock> textBlocksWithStyle;
    private String url;
    private Font font;

    public void createSchedule(
            Document document,
            Map<String, TemplateTextBlock> textBlocksWithStyle,
            LoanContractInputDto loanContractInputDto,
            String url,
            Font font
    ) {

        this.loanContractInputDto = loanContractInputDto;
        this.textBlocksWithStyle = textBlocksWithStyle;
        this.font = font;
        this.url = url;
        this.backgroundColor = new Color(175, 198, 221);
        this.paymentMonthCounter = 1;

        PdfPTable scheduleYearTable = new PdfPTable(3);
        scheduleYearTable.setTotalWidth(new float[]{250,20,250});
        scheduleYearTable.setLockedWidth(true);

        // Create mocked schedule for online editing
        if (loanContractInputDto == null) {
            createMockVersionForOnlineEditing(scheduleYearTable, textBlocksWithStyle, url);
            document.add(scheduleYearTable);
            return;
        }

        int yearCounter = 1;
        for (ScheduleYear scheduleYear : loanContractInputDto.getScheduleYears()) {
            PdfPCell scheduleYearCell = createScheduleYear(scheduleYear);
            scheduleYearTable.addCell(scheduleYearCell);
            createMissingCells(scheduleYearTable, yearCounter);
            yearCounter++;
        }
        document.add(scheduleYearTable);
    }

    private PdfPCell createScheduleYear(ScheduleYear scheduleYear) {
        PdfPTable innerTable = new PdfPTable(6);
        TemplateTextBlock textBlock = textBlocksWithStyle.get("SCHEDULE_YEARS");
        PdfPCell cell = createCellService.createCellWithStylesWhenDynamicDataGiven(font, textBlock, scheduleYear.getYear(), url);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(6);
        innerTable.addCell(cell);

        createHeaders(innerTable);
        List<Integer> sortedScheduleLines = scheduleYear.getScheduleLines()
                .stream()
                .map(sl -> Integer.parseInt(sl.getPaymentDate().split("\\.")[1]))
                .collect(Collectors.toList());
        for (int month = 1; month <= MONTHS_IN_YEAR; month++) {
            boolean addBackground = month % 2 != 0;
            if (sortedScheduleLines.contains(month)) {
                createScheduleLine(paymentMonthCounter, innerTable, textBlocksWithStyle,
                        findCorrectScheduleLine(month, scheduleYear.getScheduleLines()), addBackground);
                paymentMonthCounter++;
            } else {
                createEmptyScheduleLine(innerTable, textBlocksWithStyle, addBackground);
            }
        }
        PdfPCell tableCell = new PdfPCell(innerTable);
        tableCell.setBorder(Rectangle.NO_BORDER);
        return tableCell;
    }

    private void createHeaders(PdfPTable table) {
        List<String> oneRow = Arrays.asList("PAYMENT_NR", "DATE", "CREDIT_SUM", "INTEREST", "ADM_FEE", "PAYMENT_SUM");
        createRowNotReplacingValues(oneRow, table, false);
    }

    private void createScheduleLine(
            int paymentMonthCounter,
            PdfPTable table,
            Map<String, TemplateTextBlock> textBlocksWithStyle,
            ScheduleLine scheduleLine,
            boolean withBackground
    ) {
        List<Pair<String, String>> oneRow = Arrays.asList(
                Pair.of("SCHEDULE_PAYMENT_NR", Integer.toString(paymentMonthCounter)),
                Pair.of("SCHEDULE_PAYMENT_DATE", scheduleLine.getPaymentDate()),
                Pair.of("SCHEDULE_PRINCIPAL_AMOUNT", scheduleLine.getPrincipal().toString()),
                Pair.of("SCHEDULE_INTEREST_AMOUNT", scheduleLine.getInterest().toString()),
                Pair.of("SCHEDULE_ADMINISTRATION_FEE", scheduleLine.getAdministrationFee().toString()),
                Pair.of("SCHEDULE_PAYMENT_SUM", scheduleLine.getPayment().toString())
        );
        createRowReplacingValues(textBlocksWithStyle, oneRow, table, withBackground);
    }

    private void createEmptyScheduleLine(
            PdfPTable table,
            Map<String, TemplateTextBlock> textBlocksWithStyle,
            boolean withBackground
    ) {
        List<Pair<String, String>> oneRow = Arrays.asList(
                Pair.of("SCHEDULE_PAYMENT_NR", ""),
                Pair.of("SCHEDULE_PAYMENT_DATE", ""),
                Pair.of("SCHEDULE_PRINCIPAL_AMOUNT", ""),
                Pair.of("SCHEDULE_INTEREST_AMOUNT", ""),
                Pair.of("SCHEDULE_ADMINISTRATION_FEE", ""),
                Pair.of("SCHEDULE_PAYMENT_SUM", "")
        );
        createRowReplacingValues(textBlocksWithStyle, oneRow, table, withBackground);
    }

    private void createRowReplacingValues(
            Map<String, TemplateTextBlock> textBlocksWithStyle,
            List<Pair<String, String>> input,
            PdfPTable table,
            boolean withBackground
    ) {
        for (Pair<String, String> textBlock : input) {
            TemplateTextBlock block = textBlocksWithStyle.get(textBlock.getFirst());
            PdfPCell cell = createCellService.createCellWithStylesWhenDynamicDataGiven(font, block, textBlock.getSecond(), url);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setMinimumHeight(DEFAULT_MIN_HEIGHT);
            if (withBackground) {
                cell.setBackgroundColor(backgroundColor);
            }
            table.addCell(cell);
        }
    }

    private void createRowNotReplacingValues(
            List<String> input,
            PdfPTable table,
            boolean withBackground
    ) {
        for (String textBlock : input) {
            TemplateTextBlock block = textBlocksWithStyle.get(textBlock);
            PdfPCell cell = createCellService.createCellWithStylesNoSubstitutions(font, block, url);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setMinimumHeight(DEFAULT_MIN_HEIGHT);
            if (withBackground) {
                cell.setBackgroundColor(backgroundColor);
            }
            table.addCell(cell);
        }
    }

    private void createMissingCells(PdfPTable scheduleYearTable, int yearCounter) {
        if (yearCounter % 2 != 0) {
            scheduleYearTable.addCell(createCellService.createEmptyCellWithNoStyles());
            if (loanContractInputDto.getScheduleYears().size() == yearCounter) {
                scheduleYearTable.addCell(createCellService.createEmptyCellWithNoStyles());
            }
        }
    }

    private ScheduleLine findCorrectScheduleLine(int month, List<ScheduleLine> scheduleYear) {
        return scheduleYear.stream()
                .filter(sl -> Integer.parseInt(sl.getPaymentDate().split("\\.")[1]) == month)
                .findFirst()
                .orElse(null);
    }

    private void createMockVersionForOnlineEditing(
            PdfPTable table,
            Map<String, TemplateTextBlock> textBlocksWithStyle,
            String url
    ) {
        PdfPTable innerTable = new PdfPTable(6);
        TemplateTextBlock textBlock = textBlocksWithStyle.get("SCHEDULE_YEARS");
        PdfPCell cell = createCellService.createCellWithStylesNoSubstitutions(font, textBlock, url);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(6);
        innerTable.addCell(cell);

        createHeaders(innerTable);
        createMockRow(innerTable);
        PdfPCell tableCell = new PdfPCell(innerTable);
        tableCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(tableCell);
        table.addCell(createCellService.createEmptyCellWithNoStyles());
        table.addCell(createCellService.createEmptyCellWithNoStyles());
    }

    private void createMockRow(PdfPTable table) {
        List<String> oneRow = Arrays.asList(
                "SCHEDULE_PAYMENT_NR",
                "SCHEDULE_PAYMENT_DATE",
                "SCHEDULE_PRINCIPAL_AMOUNT",
                "SCHEDULE_INTEREST_AMOUNT",
                "SCHEDULE_ADMINISTRATION_FEE",
                "SCHEDULE_PAYMENT_SUM"
        );
        createRowNotReplacingValues(oneRow, table, false);
    }

}
