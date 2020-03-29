package generate.pdf.openpdf.config.security;

import generate.pdf.openpdf.mapper.UserRoleMapper;
import generate.pdf.openpdf.service.UserFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static generate.pdf.openpdf.config.security.CookieUtils.addCookie;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final UserRoleMapper userRoleMapper;
    private final UserFileService userFileService;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Value("${front-end.address}")
    private String frontEndAddress;

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect.");
            return;
        }

        request.getCookies();
        String username = userFileService.getEmailFromAuth(authentication);
        List<String> authorityStrings = userRoleMapper.findRolesByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (authorityStrings.isEmpty()) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            for (String auth : authorityStrings) {
                authorities.add(new SimpleGrantedAuthority(auth));
            }
        }

        String token = tokenProvider.createToken(authentication, authorities);

        String targetUrl = UriComponentsBuilder.fromUriString(frontEndAddress)
                .path("/#/home")
                .queryParam("token", token)
                .build().toUriString();

        clearAuthenticationAttributes(request, response);
        // todo smth like that response.addCookie();
//        String cookie = String.format("TTTTT=%s", token);
//        response.setHeader("Set-Cookie", cookie);
//        addCookie(response, "WWWWW", token, 50000);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

}
