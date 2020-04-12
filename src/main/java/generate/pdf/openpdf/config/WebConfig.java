package generate.pdf.openpdf.config;

import generate.pdf.openpdf.util.LanguageCodeToEnumConverter;
import generate.pdf.openpdf.util.TemplateCodeToEnumConverter;
import generate.pdf.openpdf.util.UpdateTypeToEnumConverter;
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
        registry.addConverter(new TemplateCodeToEnumConverter());
        registry.addConverter(new UpdateTypeToEnumConverter());
        registry.addConverter(new LanguageCodeToEnumConverter());
    }

}
