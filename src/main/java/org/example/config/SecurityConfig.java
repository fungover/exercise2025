package org.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Static resources
                        .requestMatchers("/css/**").permitAll()

                        // Public pages
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers("/login").permitAll()

                        // Thymeleaf form actions (WebController)
                        .requestMatchers(HttpMethod.POST, "/pets/*/feed").authenticated()
                        .requestMatchers(HttpMethod.POST, "/pets/*/play").authenticated()
                        .requestMatchers(HttpMethod.POST, "/pets/*/delete").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/pets/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/pets/add").hasRole("ADMIN")

                        // REST API endpoints
                        .requestMatchers(HttpMethod.GET, "/api/pets/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/pets/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/pets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/pets/**").hasRole("ADMIN")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                                .ignoringRequestMatchers("/api/**")
                        // CSRF enabled för Thymeleaf forms
                );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
