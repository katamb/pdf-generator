package generate.pdf.openpdf.controller;

import generate.pdf.openpdf.dto.FileResponseDto;
import generate.pdf.openpdf.dto.UserSqlFileDto;
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

    @PreAuthorize("hasAnyRole('ROLE_EDITOR', 'ROLE_DEVELOPER')")
    @GetMapping("is-sql-file-selected")
    public UserSqlFileDto getSelectedSqlFile(Principal principal) {
        return userFileService.getSelectedSqlFile(principal);
    }

    @PreAuthorize("hasAnyRole('ROLE_EDITOR', 'ROLE_DEVELOPER')")
    @GetMapping("sql-files")
    public List<UserSqlFileDto> getSqlFiles(Principal principal) {
        return userFileService.getUserSqlFiles(principal);
    }

    @PreAuthorize("hasAnyRole('ROLE_EDITOR', 'ROLE_DEVELOPER')")
    @GetMapping("download-sql/{fileName}")
    public FileResponseDto getSqlFile(
            Principal principal,
            @PathVariable String fileName
    ) {
        return userFileService.downloadFile(principal, fileName);
    }

    @PreAuthorize("hasAnyRole('ROLE_EDITOR', 'ROLE_DEVELOPER')")
    @GetMapping("download-sql/selected")
    public FileResponseDto downloadSelectedSqlFile(Principal principal) {
        return userFileService.downloadSelectedFile(principal);
    }

    @PreAuthorize("hasAnyRole('ROLE_EDITOR', 'ROLE_DEVELOPER')")
    @PutMapping("select-sql/{id}")
    public void selectSqlFile(Principal principal, @PathVariable Long id) {
        userFileService.selectSqlFile(principal, id);
    }

    @PreAuthorize("hasAnyRole('ROLE_EDITOR', 'ROLE_DEVELOPER')")
    @PostMapping("add-sql")
    public void addSqlFile(Principal principal) {
        userFileService.addSqlFile(principal);
    }

}