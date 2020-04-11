package generate.pdf.openpdf.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EnvironmentVariableProvider {

    @Value("${front-end.address}")
    private String frontendAddress;
    @Value("${front-end.path.edit}")
    private String frontendEditPath;

}
