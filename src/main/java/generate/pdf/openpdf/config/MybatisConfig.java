package generate.pdf.openpdf.config;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MybatisConfig {

    private final MybatisInterceptor mybatisInterceptor;

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.addInterceptor(mybatisInterceptor);
    }

}