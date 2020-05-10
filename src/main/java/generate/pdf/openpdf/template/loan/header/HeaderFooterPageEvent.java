package generate.pdf.openpdf.template.loan.header;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.exception.PdfGenerationException;
import generate.pdf.openpdf.service.table.CreateCellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

/**
 * Created with help from: https://memorynotfound.com/adding-header-footer-pdf-using-itext-java/
 */
public class HeaderFooterPageEvent extends PdfPageEventHelper {

    private static final Logger logger = LoggerFactory.getLogger(HeaderFooterPageEvent.class);
    private static final int HEADER_FOOTER_HEIGHT = 40;
    private static final int HEADER_FOOTER_WIDTH = 530;

    private CreateCellService createCellService;
    private Map<String, TemplateTextBlock> templateTextBlockMap;
    private Map<String, Object> inputDataAsMap;
    private String url;
    private Font font;
    private PdfTemplate template;
    private Image pageCountPlaceholder;

    public HeaderFooterPageEvent(
            CreateCellService createCellService,
            Map<String, TemplateTextBlock> templateTextBlockMap,
            Map<String, Object> inputDataAsMap,
            String url,
            Font font
    ) {
        this.createCellService = createCellService;
        this.templateTextBlockMap = templateTextBlockMap;
        this.inputDataAsMap = inputDataAsMap;
        this.url = url;
        this.font = font;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        template = writer.getDirectContent().createTemplate(16, 16);
        try {
            pageCountPlaceholder = Image.getInstance(template);
        } catch (DocumentException e) {
            logger.warn(e.getMessage(), e);
            throw new PdfGenerationException(e.getMessage(), e);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        String totalPageNumbers = String.valueOf(writer.getPageNumber() - 1);
        font.setSize(templateTextBlockMap.get("PAGE_NR").getTextSize());
        Phrase phrase = new Phrase(totalPageNumbers, font);
        ColumnText.showTextAligned(template, Element.ALIGN_RIGHT, phrase, totalWidth, 4, 0);
    }

    private void addHeader(PdfWriter writer) {
        PdfPTable header = new PdfPTable(2);
        try {
            // set defaults
            header.setWidths(new int[]{1, 3});
            header.setTotalWidth(HEADER_FOOTER_WIDTH);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(HEADER_FOOTER_HEIGHT);
            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            header.getDefaultCell().setBorderColor(Color.GRAY);

            // add image
            Image logo = Image.getInstance(getClass().getResource("/static/sampleLogo.png"));
            header.addCell(logo);

            // add text
            TemplateTextBlock textBlock = templateTextBlockMap.get("HEADER_TEXT_1");
            PdfPCell textCell = createCellService.createCellAndInsertDynamicData(font, textBlock, inputDataAsMap, url);
            textCell.setBorder(Rectangle.BOTTOM);
            textCell.setBorderColor(Color.GRAY);
            header.addCell(textCell);

            // write content
            header.writeSelectedRows(0, -1, 35, 800, writer.getDirectContent());
        } catch (DocumentException | IOException e) {
            logger.warn(e.getMessage(), e);
            throw new PdfGenerationException(e.getMessage(), e);
        }
    }

    private void addFooter(PdfWriter writer) {
        PdfPTable footer = new PdfPTable(3);
        try {
            footer.setWidths(new int[]{24, 4, 1});
            footer.setTotalWidth(HEADER_FOOTER_WIDTH);
            footer.setLockedWidth(true);

            // add text
            TemplateTextBlock text = templateTextBlockMap.get("FOOTER_TEXT_1");
            PdfPCell cell = createCellService.createCellAndInsertDynamicData(font, text, inputDataAsMap, url);
            cell.setFixedHeight(HEADER_FOOTER_HEIGHT);
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(Color.GRAY);
            footer.addCell(cell);

            // add current page count
            text = templateTextBlockMap.get("PAGE_NR");
            String currentPageNumber = Integer.toString(writer.getPageNumber());
            cell = createCellService.createCellAndInsertGivenString(font, text, currentPageNumber, url);
            cell.setFixedHeight(HEADER_FOOTER_HEIGHT);
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(Color.GRAY);
            footer.addCell(cell);

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(pageCountPlaceholder);
            cell.setFixedHeight(HEADER_FOOTER_HEIGHT);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(Color.GRAY);
            footer.addCell(totalPageCount);

            // write page
            footer.writeSelectedRows(0, -1, 35, 50, writer.getDirectContent());
        } catch (DocumentException e) {
            logger.warn(e.getMessage(), e);
            throw new PdfGenerationException(e.getMessage(), e);
        }
    }

}
