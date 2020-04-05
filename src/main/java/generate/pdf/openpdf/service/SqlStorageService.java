package generate.pdf.openpdf.service;

import generate.pdf.openpdf.config.StartupConfig;
import generate.pdf.openpdf.dto.FileResponse;
import generate.pdf.openpdf.dto.UserSqlFile;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.exception.InternalServerException;
import generate.pdf.openpdf.mapper.UserSqlFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class SqlStorageService {

    private static final Logger logger = LoggerFactory.getLogger(SqlStorageService.class);
    private final Path fileStorageLocation;
    private final UserSqlFileMapper userSqlFileMapper;

    public SqlStorageService(StartupConfig startupConfig, UserSqlFileMapper userSqlFileMapper) {
        this.userSqlFileMapper = userSqlFileMapper;

        // Full path to uploads directory
        this.fileStorageLocation = Paths.get(startupConfig.getSqlDirectory()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerException("Could not create the directory where the uploaded files will be stored.");
        }
    }

    public UserSqlFile createNewSqlForUser(String username) {
        int uniqueNum = 0;
        String escapedUsername = username.replaceAll("[^a-zA-Z0-9.-]", "_");
        String fileName = createFileName(escapedUsername, uniqueNum);
        Path targetLocation = this.fileStorageLocation.resolve(fileName);

        while (Files.exists(targetLocation)) {
            uniqueNum++;
            fileName = createFileName(escapedUsername, uniqueNum);
            targetLocation = this.fileStorageLocation.resolve(fileName);
        }

        try {
            Files.createFile(targetLocation);

            UserSqlFile userSqlFile = new UserSqlFile();
            userSqlFile.setUsername(username);
            userSqlFile.setSqlFileName(fileName);
            userSqlFileMapper.insertSqlFileReference(userSqlFile);

            return userSqlFile;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerException("Could not store file " + fileName + ". Please try again!");
        }
    }

    private String createFileName(String username, int uniqueNumber) {
        StringBuilder filenameBuilder = new StringBuilder();
        filenameBuilder.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        filenameBuilder.append("-");
        filenameBuilder.append(username);
        filenameBuilder.append("-");
        filenameBuilder.append(uniqueNumber);
        filenameBuilder.append(".sql");
        return filenameBuilder.toString();
    }

    public FileResponse loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            byte[] fileAsByteArray = Files.readAllBytes(filePath);

            FileResponse fileResponse = new FileResponse();
            fileResponse.setFileName(fileName);
            fileResponse.setFile(fileAsByteArray);
            return fileResponse;

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BadRequestException("File not found " + fileName);
        }
    }

}
