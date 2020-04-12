package generate.pdf.openpdf.service;

import generate.pdf.openpdf.mapper.UserRoleMapper;
import generate.pdf.openpdf.security.AppUser;
import generate.pdf.openpdf.dto.FileResponse;
import generate.pdf.openpdf.dto.ResponseWithMessage;
import generate.pdf.openpdf.dto.UserSqlFile;
import generate.pdf.openpdf.exception.BadRequestException;
import generate.pdf.openpdf.exception.UnauthorizedException;
import generate.pdf.openpdf.mapper.UserSqlFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserFileService {

    private final UserSqlFileMapper userSqlFileMapper;
    private final SqlStorageService sqlStorageService;
    private final UserRoleMapper userRoleMapper;

    public String getEmailFromPrincipal(Principal principal) {
        return ((AppUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUsername();
    }

    public ResponseWithMessage getUserEmail(Principal principal) {
        if (principal != null) {
            return new ResponseWithMessage(HttpStatus.OK.value(), getEmailFromPrincipal(principal));
        } else {
            throw new UnauthorizedException("You need to login to access this resource!");
        }
    }

    public List<String> findRoles(Principal principal) {
        List<String> roles = userRoleMapper.findRolesByUsername(getEmailFromPrincipal(principal));
        return roles.isEmpty() ? Collections.singletonList("ROLE_USER") : roles;
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

    public FileResponse downloadFile(Principal principal, String fileName) {
        validateTheFileExists(fileName, getEmailFromPrincipal(principal));
        return sqlStorageService.loadFileAsResource(fileName);
    }

    private void validateTheFileExists(@PathVariable String fileName, String email) {
        userSqlFileMapper.getUserFiles(email)
                .stream()
                .filter(file -> file.getSqlFileName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("This file does not exist!"));
    }

    public FileResponse downloadSelectedFile(Principal principal) {
        UserSqlFile userSqlFile = getSelectedSqlFile(principal);
        return downloadFile(principal, userSqlFile.getSqlFileName());
    }

}
