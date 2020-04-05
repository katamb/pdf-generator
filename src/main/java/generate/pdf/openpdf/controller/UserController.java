package generate.pdf.openpdf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import generate.pdf.openpdf.security.TokenProvider;
import generate.pdf.openpdf.dto.FileResponse;
import generate.pdf.openpdf.dto.JwtToken;
import generate.pdf.openpdf.dto.UserSqlFile;
import generate.pdf.openpdf.exception.UnauthorizedException;
import generate.pdf.openpdf.mapper.UserRoleMapper;
import generate.pdf.openpdf.service.UserFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private final UserFileService userFileService;
    private final ObjectMapper objectMapper;
    private final UserRoleMapper userRoleMapper;
    private final TokenProvider tokenProvider;

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String clientId;

    @PostMapping("oauth-login")
    public JwtToken oAuthLogin(@RequestBody JwtToken jwtToken) throws Exception {
        String jwt = URLEncoder.encode(jwtToken.getJwt(), StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://oauth2.googleapis.com/tokeninfo?id_token=" + jwt))
                .timeout(Duration.ofSeconds(2))
                .build();

        HttpResponse<String> res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) {
            throw new UnauthorizedException("The server received invalid jwt!");
        }
        String json = res.body();
        Map<String, String> map = objectMapper.readValue(json, Map.class);
        if (!map.get("iss").equals("accounts.google.com")) {
            throw new UnauthorizedException("The server received invalid jwt!");
        }
        if (!clientId.equals("831887232071-k6dmabuu48v05rn4h5h12evh70r1m5tj.apps.googleusercontent.com")) { //todo read value from json
            throw new UnauthorizedException("The server received invalid jwt!");
        }

        String username = map.get("email");
        String name = map.get("name");
        List<String> authorityStrings = userRoleMapper.findRolesByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (authorityStrings.isEmpty()) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            for (String auth : authorityStrings) {
                authorities.add(new SimpleGrantedAuthority(auth));
            }
        }
        String token = tokenProvider.createToken(username, name, authorities);
        JwtToken returnJwt = new JwtToken();
        returnJwt.setJwt(token);

        return returnJwt;
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