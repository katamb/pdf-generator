package generate.pdf.openpdf.util;

import generate.pdf.openpdf.enums.LanguageCode;
import generate.pdf.openpdf.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LanguageCodeToEnumConverter implements Converter<String, LanguageCode> {

    /**
     * Allow lowercase values to be mapped to uppercase enums for language codes.
     */
    @Override
    public LanguageCode convert(String source) {
        try {
            return LanguageCode.valueOf(source.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("This language code does not exist!");
        }
    }

}