package generate.pdf.openpdf.service.table;

import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;

import java.io.IOException;

public class RadioCellField implements PdfPCellEvent {
        private PdfFormField radiogroup;
        private String value;
        public RadioCellField(PdfFormField radiogroup, String value) {
            this.radiogroup = radiogroup;
            this.value = value;
        }
        public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
            final PdfWriter writer = canvases[0].getPdfWriter();
            RadioCheckField radio = new RadioCheckField(writer, rectangle, null, value);
            try {
                radiogroup.addKid(radio.getRadioField());
            } catch (final IOException ioe) {
                throw new ExceptionConverter(ioe);
            } catch (final DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
    }