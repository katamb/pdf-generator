package generate.pdf.openpdf.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
@Getter
public class StartupConfig {

    @Value( "${pdf.storage.dir}" )
    private String fileDirectory;
    @Value( "${sql.storage.dir}" )
    private String sqlDirectory;

    /**
     * Create directory for storing files.
     */
    @PostConstruct
    public void createDirectories() {
        new File(fileDirectory).mkdirs();
        new File(sqlDirectory).mkdirs();
    }

}
