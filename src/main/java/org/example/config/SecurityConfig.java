package org.example.config;

import org.example.service.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain configuredFilter(HttpSecurity http) throws Exception {
       return http
               .csrf(AbstractHttpConfigurer::disable)
               .authenticationProvider(customAuthenticationProvider)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/login", "/errors").permitAll()
                        //.requestMatchers("/books/**").permitAll()
                        //.requestMatchers(HttpMethod.POST, "/books/**").permitAll()
                        .requestMatchers("/books").authenticated()
                        .requestMatchers("/books/**").authenticated()
                        //.requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                )
               .formLogin(Customizer.withDefaults()).build();

    }



}
