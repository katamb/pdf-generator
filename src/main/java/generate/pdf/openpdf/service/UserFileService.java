package generate.pdf.openpdf.service;

import generate.pdf.openpdf.config.security.AppUser;
import generate.pdf.openpdf.dto.ResponseWithMessage;
import generate.pdf.openpdf.dto.UserSqlFile;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.exception.UnauthorizedException;
import generate.pdf.openpdf.mapper.UserSqlFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserFileService {

    private final UserSqlFileMapper userSqlFileMapper;
    private final SqlStorageService sqlStorageService;

    public String getEmailFromPrincipal(Principal principal) {
//        return ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
        return ((AppUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUsername();
    }

    public String getEmailFromAuth(Authentication authentication) {
        return ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("email");
    }

    public String getNameFromAuth(Authentication authentication) {
        return ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("name");
    }

    public ResponseWithMessage getUserEmail(Principal principal) {
        if (principal != null) {
            return new ResponseWithMessage(HttpStatus.OK.value(), getEmailFromPrincipal(principal));
        } else {
            throw new UnauthorizedException("You need to login to access this resource!");
        }
    }

    public UserSqlFile getSelectedSqlFile(Principal principal) {
        return userSqlFileMapper.getUserFiles(getEmailFromPrincipal(principal))
                .stream()
                .filter(UserSqlFile::isSelected)
                .findFirst()
                .orElseThrow(() -> new BadRequestException("SQL file needs to be chosen to edit template! This can be done on the home screen."));
    }

    public List<UserSqlFile> getUserSqlFiles(Principal principal) {
        return userSqlFileMapper.getUserFiles(getEmailFromPrincipal(principal))
                .stream()
                .sorted(Comparator.comparing(UserSqlFile::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public void selectSqlFile(Principal principal, Long id) {
        userSqlFileMapper.deSelectUserFiles(getEmailFromPrincipal(principal));
        userSqlFileMapper.selectFile(id);
    }

    public void addSqlFile(Principal principal) {
        sqlStorageService.createNewSqlForUser(getEmailFromPrincipal(principal));
    }

    public ResponseEntity<Resource> downloadFile(Principal principal, String fileName) {
        validateUserDownloadsOwnFiles(fileName, getEmailFromPrincipal(principal));
        // Load file as Resource
        Resource resource = sqlStorageService.loadFileAsResource(fileName);

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

    public ResponseEntity<Resource> downloadSelectedFile(Principal principal) {
        UserSqlFile userSqlFile = getSelectedSqlFile(principal);
        return downloadFile(principal, userSqlFile.getSqlFileName());
    }

}
