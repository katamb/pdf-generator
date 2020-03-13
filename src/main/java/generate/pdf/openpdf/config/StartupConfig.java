package generate.pdf.openpdf.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Configuration
@Getter
public class StartupConfig {

    @Value( "${storage.pdf.folder}" )
    private String fileLocation;
    @Value( "${sql.folder}" )
    private String sqlLocation;
    @Value( "${sql.archive.folder}" )
    private String sqlArchives;

    /**
     * Create directory for storing files.
     */
    @PostConstruct
    public void createDirectories() throws IOException {
        new File(fileLocation).mkdirs();
        File file = new File(sqlLocation);
        file.getParentFile().mkdirs();
        new FileWriter(file);
        new File(sqlArchives).mkdirs();
    }

}
