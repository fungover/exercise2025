package org.example.api.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.api.security.service.AuthenticationService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

public class ApiKeyRequestFilter extends GenericFilterBean {
    AuthenticationService authenticationService;

    public ApiKeyRequestFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String apiKey = httpRequest.getHeader("X-API-KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated() && !(existingAuth instanceof AnonymousAuthenticationToken)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            PrintWriter writer = httpResponse.getWriter();
            writer.print(e.getMessage());
            writer.flush();
            writer.close();
        }
    }

}
