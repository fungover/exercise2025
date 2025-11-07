package org.example.config;

import org.example.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            @NotNull HttpSecurity http,
            MustChangePasswordAuthProvider mustChangePasswordProvider,
            CustomAuthFailureHandler customAuthFailureHandler
    ) throws Exception {

        // Koppla in vår custom auth provider
        http.authenticationProvider(mustChangePasswordProvider);

        http
                .authorizeHttpRequests(auth -> auth
                        // API — endast ADMIN får radera
                        .requestMatchers(HttpMethod.DELETE, "/api/catches/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")

                        // Admin-dashboard
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Offentliga sidor
                        .requestMatchers("/", "/login", "/login/**", "/register/**", "/error").permitAll()

                        // Change password-sidan måste vara publik för redirect att funka
                        .requestMatchers("/change-password", "/change-password/**").permitAll()

                        // Allt annat kräver inloggning
                        .anyRequest().authenticated()
                )

                // Form login
                .formLogin(form -> form
                        .failureHandler(customAuthFailureHandler) // catch "Password must be changed"
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                // HTTP Basic (t.ex. Postman)
                .httpBasic(Customizer.withDefaults())

                // CSRF avstängt för API
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**", "/login/**", "/register/**", "/change-password/**")
                )

                // Logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

// TODO: Create mapping to /change-password
// TODO: When create new password, admin should enter old password with the new password two times
// TODO: After should redirect to login page
