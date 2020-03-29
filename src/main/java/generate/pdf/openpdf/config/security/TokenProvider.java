package generate.pdf.openpdf.config.security;

import generate.pdf.openpdf.service.UserFileService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final UserFileService userFileService;

    @Value("${jwt.lasts-in-ms}")
    private Long lastingTime;
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Authentication authentication, List<GrantedAuthority> roles) {
        String email = userFileService.getEmailFromAuth(authentication);
        String name = userFileService.getNameFromAuth(authentication);
        List<String> authorities = roles.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + lastingTime);

        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .claim("roles", authorities)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public AppUser getAppUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String auth : (List<String>) claims.get("roles")) {
            authorities.add(new SimpleGrantedAuthority(auth));
        }

        return new AppUser(
                claims.getSubject(),
                claims.get("name").toString(),
                authorities
        );
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.warn("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.warn("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.warn("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.warn("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.warn("JWT claims string is empty.");
        }
        return false;
    }

}
