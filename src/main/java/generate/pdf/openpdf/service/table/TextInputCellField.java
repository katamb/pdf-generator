package generate.pdf.openpdf.service.table;

import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;

import java.io.IOException;

public class TextInputCellField implements PdfPCellEvent {

    private String fieldName;

    public TextInputCellField(String fieldName) {
        this.fieldName = fieldName;
    }

    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        final PdfWriter writer = canvases[0].getPdfWriter();
        final TextField textField = new TextField(writer, rectangle, fieldName);
        //textField.setOptions(TextField.MULTILINE);
        try {
            final PdfFormField field = textField.getTextField();
            writer.addAnnotation(field);
        } catch (IOException ioe) {
            throw new ExceptionConverter(ioe);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }
}