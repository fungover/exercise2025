package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/products/form", "/css/**", "/login").permitAll() // Remove ("/" & "/products/form") to prompt user to login
                        .requestMatchers("/products/**", "/locations/**").authenticated()
                        .anyRequest().permitAll() // fallback
                )
                .formLogin(form -> form
                        .loginPage("/login") // Login page
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
