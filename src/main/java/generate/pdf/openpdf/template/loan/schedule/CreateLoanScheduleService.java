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

import static generate.pdf.openpdf.util.FormattingUtil.formatBigDecimal;

@Service
@RequiredArgsConstructor
public class CreateLoanScheduleService {

    private static final int MONTHS_IN_YEAR = 12;
    private static final int DEFAULT_MIN_HEIGHT = 14;
    private final CreateCellService createCellService;

    private Color backgroundColor;
    private int paymentMonthCounter;
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
        this.textBlocksWithStyle = textBlocksWithStyle;
        this.font = font;
        this.url = url;
        this.backgroundColor = new Color(194, 218, 241);
        this.paymentMonthCounter = 1;

        PdfPTable scheduleYearTable = new PdfPTable(3);
        scheduleYearTable.setTotalWidth(new float[]{250,20,250});
        scheduleYearTable.setLockedWidth(true);
        scheduleYearTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        // Create mocked schedule for online editing
        if (loanContractInputDto == null) {
            createMockVersionForOnlineEditing(scheduleYearTable);
            document.add(scheduleYearTable);
            return;
        }

        // Create real schedule with data
        int yearCounter = 1;
        for (ScheduleYear scheduleYear : loanContractInputDto.getScheduleYears()) {
            PdfPTable scheduleYearCell = createScheduleYear(scheduleYear);
            scheduleYearTable.addCell(scheduleYearCell);
            // Add column for spacing
            if (yearCounter % 2 != 0) {
                scheduleYearTable.addCell(createCellService.createEmptyCellWithNoStyles());
            }
            yearCounter++;
        }
        scheduleYearTable.completeRow();
        document.add(scheduleYearTable);
    }

    private PdfPTable createScheduleYear(ScheduleYear scheduleYear) {
        PdfPTable innerTable = createScheduleTableWithHeader(scheduleYear.getYear());
        createDescriptionRow(innerTable);

        List<Integer> sortedScheduleLines = sortScheduleLines(scheduleYear);
        for (int month = 1; month <= MONTHS_IN_YEAR; month++) {
            boolean addBackground = month % 2 == 0;
            if (sortedScheduleLines.contains(month)) {
                ScheduleLine currentScheduleLine = findCorrectScheduleLine(month, scheduleYear.getScheduleLines());
                createScheduleLine(paymentMonthCounter, innerTable, textBlocksWithStyle, currentScheduleLine, addBackground);
                paymentMonthCounter++;
            } else {
                createEmptyScheduleLine(innerTable, textBlocksWithStyle, addBackground);
            }
        }

        return innerTable;
    }

    private PdfPTable createScheduleTableWithHeader(String year) {
        PdfPTable innerTable = new PdfPTable(6);
        TemplateTextBlock textBlock = textBlocksWithStyle.get("SCHEDULE_YEARS");
        PdfPCell cell = getPdfPCell(year, textBlock);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(6);
        innerTable.addCell(cell);
        return innerTable;
    }

    private PdfPCell getPdfPCell(String year, TemplateTextBlock textBlock) {
        if (year == null) {
            return createCellService.createCellMakeNoSubstitutions(font, textBlock, url);
        } else {
            return createCellService.createCellAndInsertGivenString(font, textBlock, year, url);
        }
    }

    private void createDescriptionRow(PdfPTable table) {
        List<String> oneRow = Arrays.asList("PAYMENT_NR", "DATE", "CREDIT_SUM", "INTEREST", "ADM_FEE", "PAYMENT_SUM");
        createRowNotReplacingValues(oneRow, table);
    }

    private List<Integer> sortScheduleLines(ScheduleYear scheduleYear) {
        return scheduleYear.getScheduleLines()
                .stream()
                .map(sl -> Integer.parseInt(sl.getPaymentDate().split("\\.")[1]))
                .collect(Collectors.toList());
    }

    private ScheduleLine findCorrectScheduleLine(int month, List<ScheduleLine> scheduleYear) {
        return scheduleYear.stream()
                .filter(sl -> Integer.parseInt(sl.getPaymentDate().split("\\.")[1]) == month)
                .findFirst()
                .orElse(null);
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
                Pair.of("SCHEDULE_PRINCIPAL_AMOUNT", formatBigDecimal(scheduleLine.getPrincipal())),
                Pair.of("SCHEDULE_INTEREST_AMOUNT", formatBigDecimal(scheduleLine.getInterest())),
                Pair.of("SCHEDULE_ADMINISTRATION_FEE", formatBigDecimal(scheduleLine.getAdministrationFee())),
                Pair.of("SCHEDULE_PAYMENT_SUM", formatBigDecimal(scheduleLine.getPayment()))
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
            List<Pair<String, String>> textBlocks,
            PdfPTable table,
            boolean withBackground
    ) {
        for (Pair<String, String> textBlock : textBlocks) {
            TemplateTextBlock block = textBlocksWithStyle.get(textBlock.getFirst());
            PdfPCell cell = createCellService.createCellAndInsertGivenString(font, block, textBlock.getSecond(), url);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setMinimumHeight(DEFAULT_MIN_HEIGHT);
            if (withBackground) {
                cell.setBackgroundColor(backgroundColor);
            }
            table.addCell(cell);
        }
    }

    private void createRowNotReplacingValues(List<String> input, PdfPTable table) {
        for (String textBlock : input) {
            TemplateTextBlock block = textBlocksWithStyle.get(textBlock);
            PdfPCell cell = createCellService.createCellMakeNoSubstitutions(font, block, url);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setMinimumHeight(DEFAULT_MIN_HEIGHT);
            table.addCell(cell);
        }
    }

    private void createMockVersionForOnlineEditing(PdfPTable table) {
        PdfPTable innerTable = createScheduleTableWithHeader(null);

        createDescriptionRow(innerTable);
        createMockRow(innerTable);

        table.addCell(innerTable);
        table.completeRow();
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
        createRowNotReplacingValues(oneRow, table);
    }

}
