package integration_tests;

import generate.pdf.openpdf.security.AppUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Util {

    public static String TEST_USERNAME = "INTEG_TEST";
    public static String TEST_USER_NAME = "Integration Test";

    public static void setUserContextForIntegTests() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        AppUser appUser = new AppUser(TEST_USERNAME, TEST_USER_NAME, authorities);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(appUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static void setEditorContextForIntegTests() {
        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_EDITOR")
        );
        AppUser appUser = new AppUser(TEST_USERNAME, TEST_USER_NAME, authorities);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(appUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
