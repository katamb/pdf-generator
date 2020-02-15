package generate.pdf.openpdf.util;

import generate.pdf.openpdf.enums.PrintoutType;
import generate.pdf.openpdf.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PrintoutTypeToEnumConverter implements Converter<String, PrintoutType> {

    /**
     * Allow lowercase values to be mapped to uppercase enums for printout types.
     */
    @Override
    public PrintoutType convert(String source) {
        try {
            return PrintoutType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("This printout type does not exist!");
        }
    }

}