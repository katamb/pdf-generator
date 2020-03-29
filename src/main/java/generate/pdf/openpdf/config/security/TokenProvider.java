package generate.pdf.openpdf.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import generate.pdf.openpdf.service.UserFileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final UserFileService userFileService;
    private final ObjectMapper objectMapper;

    @Value("${jwt.lasts-in-ms}")
    private Long lastingTime;
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(String email, String name, List<GrantedAuthority> roles) {
        List<String> authorities = roles.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + lastingTime);

        return JWT.create()
                .withSubject(email)
                .withClaim("name", name)
                .withClaim("roles", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(expiryDate)
                .sign(HMAC512(secret));
    }

    public AppUser getAppUserFromToken(String token) {
        DecodedJWT claims = JWT.decode(token);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String auth : claims.getClaim("roles").asArray(String.class)) {
            authorities.add(new SimpleGrantedAuthority(auth));
        }

        return new AppUser(
                claims.getSubject(),
                claims.getClaim("name").asString(),
                authorities
        );
    }

    public boolean validateToken(String authToken) {
        try {
            Algorithm algorithmHS = HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithmHS).build();
            verifier.verify(authToken);
            return true;
        } catch (AlgorithmMismatchException ex) {
            logger.warn("Invalid JWT");
        } catch (SignatureVerificationException ex) {
            logger.warn("Invalid JWT");
        } catch (TokenExpiredException ex) {
            logger.warn("Expired JWT");
        } catch (InvalidClaimException ex) {
            logger.warn("Invalid JWT");
        }
        return false;
    }

}
