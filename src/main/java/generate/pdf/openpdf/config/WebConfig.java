package generate.pdf.openpdf.config;

import generate.pdf.openpdf.util.LanguageCodeToEnumConverter;
import generate.pdf.openpdf.util.PrintoutTypeToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Register custom converter.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PrintoutTypeToEnumConverter());
        registry.addConverter(new LanguageCodeToEnumConverter());
    }

}