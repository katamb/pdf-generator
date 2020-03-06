package generate.pdf.openpdf.template.loan.header;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import generate.pdf.openpdf.dto.TemplateTextBlock;
import generate.pdf.openpdf.exception.PdfGenerationException;
import generate.pdf.openpdf.service.table.CreateCellService;

import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with help from: https://memorynotfound.com/adding-header-footer-pdf-using-itext-java/
 */
public class HeaderFooterPageEvent extends PdfPageEventHelper {

    private static final Logger logger = Logger.getLogger(String.valueOf(HeaderFooterPageEvent.class));
    private static final int HEADER_FOOTER_HEIGHT = 40;
    private static final int HEADER_FOOTER_WIDTH = 530;

    private CreateCellService createCellService;
    private Map<String, TemplateTextBlock> textBlocksWithStyle;
    private Map<String, Object> inputDataAsMap;
    private String url;
    private Font font;
    private PdfTemplate template;
    private Image total;

    public HeaderFooterPageEvent(
            CreateCellService createCellService,
            Map<String, TemplateTextBlock> textBlocksWithStyle,
            Map<String, Object> inputDataAsMap,
            String url,
            Font font
    ) {
        this.createCellService = createCellService;
        this.textBlocksWithStyle = textBlocksWithStyle;
        this.inputDataAsMap = inputDataAsMap;
        this.url = url;
        this.font = font;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        template = writer.getDirectContent().createTemplate(16, 16);
        try {
            total = Image.getInstance(template);
        } catch (DocumentException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
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
        font.setSize(textBlocksWithStyle.get("PAGE_NR").getTextSize());
        Phrase phrase = new Phrase(totalPageNumbers, font);
        ColumnText.showTextAligned(template, Element.ALIGN_RIGHT, phrase, totalWidth, 6, 0);
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
            PdfPCell text = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, textBlocksWithStyle.get("HEADER_TEXT_1"), inputDataAsMap, url);
            text.setBorder(Rectangle.BOTTOM);
            text.setBorderColor(Color.GRAY);
            header.addCell(text);

            // write content
            header.writeSelectedRows(0, -1, 35, 800, writer.getDirectContent());
        } catch (DocumentException | IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
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
            TemplateTextBlock text = textBlocksWithStyle.get("FOOTER_TEXT_1");
            PdfPCell cell = createCellService.createCellWithStylesDynamicDataFromMapIfPossible(font, text, inputDataAsMap, url);
            cell.setFixedHeight(HEADER_FOOTER_HEIGHT);
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(Color.GRAY);
            footer.addCell(cell);

            // add current page count
            text = textBlocksWithStyle.get("PAGE_NR");
            String currentPageNumber = Integer.toString(writer.getPageNumber());
            cell = createCellService.createCellWithStylesWhenDynamicDataGiven(font, text, currentPageNumber, url);
            cell.setFixedHeight(HEADER_FOOTER_HEIGHT);
            cell.setBorder(Rectangle.TOP);
            cell.setBorderColor(Color.GRAY);
            footer.addCell(cell);

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            cell.setFixedHeight(HEADER_FOOTER_HEIGHT);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(Color.GRAY);
            footer.addCell(totalPageCount);

            // write page
            footer.writeSelectedRows(0, -1, 35, 50, writer.getDirectContent());
        } catch (DocumentException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new PdfGenerationException(e.getMessage(), e);
        }
    }
}