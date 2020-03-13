package generate.pdf.openpdf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
public class UserController {

    @Value("${front-end.address}")
    private String frontEndAddress;

    @GetMapping("/oauth-login")
    public void oAuthLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(frontEndAddress + "/#/home");
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

}