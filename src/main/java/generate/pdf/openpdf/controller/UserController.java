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

import javax.servlet.http.HttpServletRequest;
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
            String email = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
            return new ResponseWithMessage(HttpStatus.OK.value(), email);
        } else {
            throw new UnauthorizedException("You need to login to access this resource!");
        }
    }

    @GetMapping("sql-files")
    public List<UserSqlFile> getSqlFiles(Principal principal) {
        String email = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
        return userSqlFileMapper.getUserFiles(email)
                .stream()
                .sorted(Comparator.comparing(UserSqlFile::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @PutMapping("select-sql/{id}")
    public void getSqlFiles(Principal principal, @PathVariable Long id) {
        String email = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
        userSqlFileMapper.deSelectUserFiles(email);
        userSqlFileMapper.selectFile(id);
    }

    @PostMapping("add-sql")
    public void addSqlFile(Principal principal) {
        String email = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
        fileStorageService.createNewSqlForUser(email);
    }

    @GetMapping("download-sql/{fileName}")
    public ResponseEntity<Resource> getSqlFiles(
            HttpServletRequest request,
            Principal principal,
            @PathVariable String fileName
    ) {
        // v-bind:href=`${BACKEND_URL}/api/v1/download-sql/${}`

        String email = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
        userSqlFileMapper.getUserFiles(email)
                .stream()
                .filter(file -> file.getSqlFileName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Only allowed to download your own files!"));
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = getContentType(request, resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String getContentType(HttpServletRequest request, Resource resource) {
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new BadRequestException("Unknown file type!");
        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

}