package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.dto.ResponseWithMessage;
import generate.pdf.openpdf.dto.UserSqlFile;
import generate.pdf.openpdf.service.UserFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserFileService userFileService;

    @Value("${front-end.address}")
    private String frontEndAddress;

    @GetMapping("oauth-login-redirect")
    public void oAuthLoginRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:7701/api/v1/oauth-login");
    }

    @GetMapping("oauth-login")
    public void oAuthLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(frontEndAddress + "/#/home");
    }

    @GetMapping("user/email")
    public ResponseWithMessage user(Principal principal) {
        return userFileService.getUserEmail(principal);
    }

    @GetMapping("is-sql-file-selected")
    public UserSqlFile getSelectedSqlFile(Principal principal) {
        return userFileService.getSelectedSqlFile(principal);
    }

    @GetMapping("sql-files")
    public List<UserSqlFile> getSqlFiles(Principal principal) {
        return userFileService.getUserSqlFiles(principal);
    }

    @GetMapping("download-sql/{fileName}")
    public ResponseEntity<Resource> getSqlFile(
            Principal principal,
            @PathVariable String fileName
    ) {
        return userFileService.downloadFile(principal, fileName);
    }

    @GetMapping("download-sql/selected")
    public ResponseEntity<Resource> downloadSelectedSqlFile(Principal principal) {
        return userFileService.downloadSelectedFile(principal);
    }

    @PutMapping("select-sql/{id}")
    public void selectSqlFile(Principal principal, @PathVariable Long id) {
        userFileService.selectSqlFile(principal, id);
    }

    @PostMapping("add-sql")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void addSqlFile(Principal principal) {
        userFileService.addSqlFile(principal);
    }

}