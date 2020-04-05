package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.dto.FileResponse;
import generate.pdf.openpdf.dto.UserSqlFile;
import generate.pdf.openpdf.service.UserFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserFileService userFileService;

    @GetMapping("user/roles")
    public List<String> findUserRoles(Principal principal) {
        return userFileService.findRoles(principal);
    }

    @GetMapping("is-sql-file-selected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserSqlFile getSelectedSqlFile(Principal principal) {
        return userFileService.getSelectedSqlFile(principal);
    }

    @GetMapping("sql-files")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<UserSqlFile> getSqlFiles(Principal principal) {
        return userFileService.getUserSqlFiles(principal);
    }

    @GetMapping("download-sql/{fileName}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public FileResponse getSqlFile(
            Principal principal,
            @PathVariable String fileName
    ) {
        return userFileService.downloadFile(principal, fileName);
    }

    @GetMapping("download-sql/selected")
    @PreAuthorize("hasRole('ROLE_USER')")
    public FileResponse downloadSelectedSqlFile(Principal principal) {
        return userFileService.downloadSelectedFile(principal);
    }

    @PutMapping("select-sql/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void selectSqlFile(Principal principal, @PathVariable Long id) {
        userFileService.selectSqlFile(principal, id);
    }

    @PostMapping("add-sql")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void addSqlFile(Principal principal) {
        userFileService.addSqlFile(principal);
    }

}