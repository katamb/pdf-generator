package generate.pdf.openpdf.service;

import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.FileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class PdfStorageService {

    private static final Logger logger = LoggerFactory.getLogger(PdfStorageService.class);
    private final Path fileStorageLocation;
    private final FileDownloadService fileDownloadService;

    public PdfStorageService(
            StartupConfig startupConfig,
            FileDownloadService fileDownloadService
    ) {
        this.fileDownloadService = fileDownloadService;

        // Full path to uploads directory
        this.fileStorageLocation = Paths.get(startupConfig.getPdfDirectory()).toAbsolutePath().normalize();
    }

    public FileResponse loadFileAsResource(String fileName) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return fileDownloadService.downloadFile(filePath, fileName);
    }

}
