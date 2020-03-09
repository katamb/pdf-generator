package generate.pdf.openpdf.service.table;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;
import generate.pdf.openpdf.exception.PdfGenerationException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextInputCellField implements PdfPCellEvent {

    private static final Logger logger = Logger.getLogger(String.valueOf(TextInputCellField.class));

    private String fieldName;
    private float fontSize;
    private boolean multiLine;

    public TextInputCellField(String fieldName, float fontSize, boolean multiLine) {
        this.fieldName = fieldName;
        this.fontSize = fontSize;
        this.multiLine = multiLine;
    }

    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        final PdfWriter writer = canvases[0].getPdfWriter();
        final TextField textField = new TextField(writer, rectangle, fieldName);
        textField.setFontSize(fontSize);
        if (multiLine) {
            textField.setOptions(TextField.MULTILINE);
        }
        try {
            final PdfFormField field = textField.getTextField();
            writer.addAnnotation(field);
        } catch (IOException | DocumentException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new PdfGenerationException(e.getMessage(), e);
        }
    }
}