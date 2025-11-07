package org.example.config;

import org.example.services.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class MustChangePasswordAuthProvider implements AuthenticationProvider {

    private final DaoAuthenticationProvider delegate;

    public MustChangePasswordAuthProvider(UserService userService,
                                          PasswordEncoder passwordEncoder) {
        this.delegate = new DaoAuthenticationProvider();
        this.delegate.setUserDetailsService(userService);
        this.delegate.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Debug, valfritt:
        System.out.println("🔥 MustChangePasswordAuthProvider AUTHENTICATE ENTERED");

        Authentication result = delegate.authenticate(authentication);

        SecurityUser user = (SecurityUser) result.getPrincipal();
        System.out.println("mustChangePassword = " + user.mustChangePassword());

        if (user.mustChangePassword()) {
            System.out.println("BLOCKING LOGIN — must change password!");
            throw new BadCredentialsException("PASSWORD_MUST_CHANGE");
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return delegate.supports(authentication);
    }
}