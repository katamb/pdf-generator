package generate.pdf.openpdf.service.table;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RadioCheckField;
import generate.pdf.openpdf.exception.PdfGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.logging.Level;

public class RadioCellField implements PdfPCellEvent {

    private static final Logger logger = LoggerFactory.getLogger(RadioCellField.class);

    private PdfFormField radioGroup;
    private String value;

    public RadioCellField(PdfFormField radioGroup, String value) {
        this.radioGroup = radioGroup;
        this.value = value;
    }

    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        final PdfWriter writer = canvases[0].getPdfWriter();
        RadioCheckField radio = new RadioCheckField(writer, rectangle, null, value);
        try {
            radioGroup.addKid(radio.getRadioField());
        } catch (IOException | DocumentException e) {
            logger.warn(e.getMessage(), e);
            throw new PdfGenerationException(e.getMessage(), e);
        }
    }

}