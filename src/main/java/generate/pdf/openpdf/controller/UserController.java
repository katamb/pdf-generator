package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.dto.ResponseWithMessage;
import generate.pdf.openpdf.dto.UserSqlFile;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.exception.UnauthorizedException;
import generate.pdf.openpdf.mapper.UserSqlFileMapper;
import generate.pdf.openpdf.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserSqlFileMapper userSqlFileMapper;
    private final FileStorageService fileStorageService;

    @Value("${front-end.address}")
    private String frontEndAddress;

    @GetMapping("oauth-login")
    public void oAuthLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(frontEndAddress + "/#/home");
    }

    @GetMapping("user/email")
    public ResponseWithMessage user(Principal principal) {
        if (principal != null) {
            String email = getEmail((OAuth2AuthenticationToken) principal);
            return new ResponseWithMessage(HttpStatus.OK.value(), email);
        } else {
            throw new UnauthorizedException("You need to login to access this resource!");
        }
    }

    @GetMapping("is-sql-file-selected")
    public UserSqlFile isSqlFileSelected(Principal principal) {
        String username = getEmail((OAuth2AuthenticationToken) principal);
        return userSqlFileMapper.getUserFiles(username)
                .stream()
                .filter(UserSqlFile::isSelected)
                .findFirst()
                .orElseThrow(() -> new BadRequestException("SQL file needs to be chosen to edit template! This can be done on the home screen."));
    }

    @GetMapping("sql-files")
    public List<UserSqlFile> getSqlFiles(Principal principal) {
        String email = getEmail((OAuth2AuthenticationToken) principal);
        return userSqlFileMapper.getUserFiles(email)
                .stream()
                .sorted(Comparator.comparing(UserSqlFile::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @PutMapping("select-sql/{id}")
    public void getSqlFiles(Principal principal, @PathVariable Long id) {
        String email = getEmail((OAuth2AuthenticationToken) principal);
        userSqlFileMapper.deSelectUserFiles(email);
        userSqlFileMapper.selectFile(id);
    }

    @PostMapping("add-sql")
    public void addSqlFile(Principal principal) {
        String email = getEmail((OAuth2AuthenticationToken) principal);
        fileStorageService.createNewSqlForUser(email);
    }

    @GetMapping("download-sql/{fileName}")
    public ResponseEntity<Resource> getSqlFile(
            Principal principal,
            @PathVariable String fileName
    ) {
        String email = getEmail((OAuth2AuthenticationToken) principal);
        return downloadFile(fileName, email);
    }

    @GetMapping("download-sql/selected")
    public ResponseEntity<Resource> getSelectedSqlFile(Principal principal) {
        String email = getEmail((OAuth2AuthenticationToken) principal);
        UserSqlFile userSqlFile = userSqlFileMapper.getSelectedFile(email);
        return downloadFile(userSqlFile.getSqlFileName(), email);
    }

    private ResponseEntity<Resource> downloadFile(@PathVariable String fileName, String email) {
        validateUserDownloadsOwnFiles(fileName, email);
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private void validateUserDownloadsOwnFiles(@PathVariable String fileName, String email) {
        userSqlFileMapper.getUserFiles(email)
                .stream()
                .filter(file -> file.getSqlFileName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Only allowed to download your own files!"));
    }

    private String getEmail(OAuth2AuthenticationToken principal) {
        return principal.getPrincipal().getAttribute("email");
    }

}