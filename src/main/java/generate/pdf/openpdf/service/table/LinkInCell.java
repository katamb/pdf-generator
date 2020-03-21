package generate.pdf.openpdf.service.table;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfWriter;
import lombok.Getter;

// Code from https://stackoverflow.com/questions/35288194/how-to-add-url-to-pdf-document-using-itext
@Getter
public class LinkInCell implements PdfPCellEvent {

    private String url;

    public LinkInCell(String url) {
        this.url = url;
    }

    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        PdfWriter writer = canvases[0].getPdfWriter();
        PdfAction action = new PdfAction(url);
        PdfAnnotation link = PdfAnnotation.createLink(
            writer, position, PdfAnnotation.HIGHLIGHT_INVERT, action);
        writer.addAnnotation(link);
    }
}