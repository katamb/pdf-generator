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

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @GetMapping("is-sql-file-selected")
    public UserSqlFile getSelectedSqlFile(Principal principal) {
        return userFileService.getSelectedSqlFile(principal);
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @GetMapping("sql-files")
    public List<UserSqlFile> getSqlFiles(Principal principal) {
        return userFileService.getUserSqlFiles(principal);
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @GetMapping("download-sql/{fileName}")
    public FileResponse getSqlFile(
            Principal principal,
            @PathVariable String fileName
    ) {
        return userFileService.downloadFile(principal, fileName);
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @GetMapping("download-sql/selected")
    public FileResponse downloadSelectedSqlFile(Principal principal) {
        return userFileService.downloadSelectedFile(principal);
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @PutMapping("select-sql/{id}")
    public void selectSqlFile(Principal principal, @PathVariable Long id) {
        userFileService.selectSqlFile(principal, id);
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @PostMapping("add-sql")
    public void addSqlFile(Principal principal) {
        userFileService.addSqlFile(principal);
    }

}