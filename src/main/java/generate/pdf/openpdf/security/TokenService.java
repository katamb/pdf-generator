package generate.pdf.openpdf.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import generate.pdf.openpdf.exception.InternalServerException;
import generate.pdf.openpdf.exception.UnauthorizedException;
import generate.pdf.openpdf.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final String GENERIC_INVALID_TOKEN_RESPONSE = "The server received invalid jwt!";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private final UserRoleMapper userRoleMapper;

    @Value("${security.oauth2.google.clientId}")
    private String clientId;

    public AppUser getAppUserFromToken(String token) {
        DecodedJWT claims = JWT.decode(token);
        String username = claims.getClaim("email").asString();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> authorityStrings = userRoleMapper.findRolesByUsername(username);
        if (authorityStrings.isEmpty()) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            for (String auth : authorityStrings) {
                authorities.add(new SimpleGrantedAuthority(auth));
            }
        }

        return new AppUser(
                username,
                claims.getClaim("name").asString(),
                authorities
        );
    }

    public boolean validateToken(String authToken) {
        String encodedToken = URLEncoder.encode(authToken, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://oauth2.googleapis.com/tokeninfo?id_token=" + encodedToken))
                .timeout(Duration.ofSeconds(5))
                .build();

        validateKeyOnGoogleSide(request);

        DecodedJWT claims = JWT.decode(authToken);
        if (!"accounts.google.com".equals(claims.getClaim("iss").asString())) {
            throw new UnauthorizedException(GENERIC_INVALID_TOKEN_RESPONSE);
        }
        if (!clientId.equals(claims.getClaim("aud").asString())) {
            throw new UnauthorizedException(GENERIC_INVALID_TOKEN_RESPONSE);
        }

        return true;
    }

    private HttpResponse<String> validateKeyOnGoogleSide(HttpRequest request) {
        try {
            HttpResponse<String> res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() != 200) {
                throw new UnauthorizedException(GENERIC_INVALID_TOKEN_RESPONSE);
            }

            return res;
        } catch (IOException | InterruptedException e) {
            throw new InternalServerException("Unable to access google oauth server!");
        }
    }

}
