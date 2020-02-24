package generate.pdf.openpdf.util;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor
public class FormattingUtil {

    public static String formatBigDecimal(BigDecimal number) {
        number = number.setScale(2, RoundingMode.HALF_UP);
        return number.toString();
    }

}
