package generate.pdf.openpdf.service;

import generate.pdf.openpdf.dto.FileResponseDto;
import generate.pdf.openpdf.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FileDownloadServiceTest {

    private String testFolder;
    private Path pathToTestFolder;
    private FileDownloadService fileDownloadService;

    @BeforeEach
    void initUseCase() throws IOException{
        testFolder = UUID.randomUUID().toString();
        pathToTestFolder = Paths.get(testFolder).toAbsolutePath().normalize();
        assumeFalse(Files.exists(pathToTestFolder));
        fileDownloadService = new FileDownloadService();
        Files.createDirectories(pathToTestFolder);
    }

    @Test
    void givenFilename_whenNoFileExists_thenExceptionGetsThrown() throws IOException {
        String randomFilename = "randomname.sql";
        Path filePath = pathToTestFolder.resolve(randomFilename).normalize();
        assertThrows(BadRequestException.class, () -> fileDownloadService.downloadFile(filePath, randomFilename));

        cleanup();
    }

    @Test
    void givenFilename_whenFileExists_thenFileGetsReturned() throws IOException {
        String randomFilename = "randomname.sql";
        Path filePath = pathToTestFolder.resolve(randomFilename).normalize();
        Files.createFile(filePath);
        FileResponseDto response = fileDownloadService.downloadFile(filePath, randomFilename);
        assertNotNull("This file is supposed to be found!", response);

        cleanup();
    }

    void cleanup() throws IOException {
        Files.walk(pathToTestFolder)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        assertFalse(
                String.format("Unable to delete directory '%s'!", testFolder),
                Files.exists(pathToTestFolder)
        );
    }

}
