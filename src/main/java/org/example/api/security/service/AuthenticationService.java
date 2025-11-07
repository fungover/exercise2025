package org.example.api.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.api.security.auth.ApiKeyAuthentication;
import org.example.entities.User;
import org.example.repository.user.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
    this.userRepository = userRepository;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);

        if (apiKey == null) {
            throw new BadCredentialsException("Unauthorized");
        }

        Optional<User> user = userRepository.findByApiKey(apiKey);

        if (user.isEmpty()) {
            throw new BadCredentialsException("Unauthorized");
        }

        User foundUser = user.get();

        return new ApiKeyAuthentication(apiKey, foundUser.getAuthorities());
    }

    public String generateApiKey() {
        return java.util.UUID.randomUUID().toString();
    }

}
