package generate.pdf.openpdf.util;

import generate.pdf.openpdf.enums.TemplateCode;
import generate.pdf.openpdf.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TemplateCodeToEnumConverter implements Converter<String, TemplateCode> {

    /**
     * Allow lowercase values to be mapped to uppercase enums for printout types.
     */
    @Override
    public TemplateCode convert(String source) {
        try {
            return TemplateCode.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("This printout type does not exist!");
        }
    }

}