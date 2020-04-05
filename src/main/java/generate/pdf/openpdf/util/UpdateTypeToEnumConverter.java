package generate.pdf.openpdf.util;

import generate.pdf.openpdf.enums.UpdateType;
import generate.pdf.openpdf.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateTypeToEnumConverter implements Converter<String, UpdateType> {

    /**
     * Allow lowercase values to be mapped to uppercase enums for update types.
     */
    @Override
    public UpdateType convert(String source) {
        try {
            return UpdateType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("This printout type does not exist!");
        }
    }

}