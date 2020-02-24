package generate.pdf.openpdf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class StartupConfig {

    @Value( "${storage.pdf.folder}" )
    private String fileLocation;

    /**
     * Create directory for storing files.
     */
    @PostConstruct
    public void createDirectories() {
        new File(fileLocation).mkdirs();
    }

}
