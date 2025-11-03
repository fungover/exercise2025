package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


/**
 * this allows get /api/animals for everyone
 * Requires ADMIN for POST/PUT/DELETE
 * Uses HTTP basic (easier for tests)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
            .disable() //for API simplicity, if i want forms i have to enable
            .authorizeHttpRequests(
              auth -> auth.requestMatchers(HttpMethod.GET, "/api/animals/**")
                          .permitAll()
                          .requestMatchers("/api/**")
                          .hasRole("ADMIN")
                          .anyRequest()
                          .authenticated())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                                .username("admin")
                                .password("adminpass")
                                .roles("ADMIN")
                                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                               .username("user")
                               .password("userpass")
                               .roles("USER")
                               .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
}
