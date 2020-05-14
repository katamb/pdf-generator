package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.FileResponseDto;
import generate.pdf.openpdf.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class FileDownloadService {

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadService.class);

    public FileResponseDto downloadFile(Path filePath, String fileName) {
        try {
            byte[] fileAsByteArray = Files.readAllBytes(filePath);

            FileResponseDto fileResponse = new FileResponseDto();
            fileResponse.setFileName(fileName);
            fileResponse.setFile(fileAsByteArray);
            return fileResponse;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BadRequestException("File not found " + fileName);
        }
    }

}
