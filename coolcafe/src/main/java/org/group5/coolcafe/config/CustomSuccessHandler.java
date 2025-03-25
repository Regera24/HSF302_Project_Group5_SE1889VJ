package org.group5.coolcafe.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "/dashboard";

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_STORE_OWNER")) {
                redirectUrl = "/dashboard";
                break;
            } else if (role.equals("ROLE_MANAGER")) {
                redirectUrl = "/account/list";
                break;
            } else if (role.equals("ROLE_EMPLOYEE")) {
                redirectUrl = "/manage-booking";
                break;
            } else if (role.equals("ROLE_CUSTOMER")) {
                redirectUrl = "/";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}
