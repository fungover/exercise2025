package org.example.config;

import org.example.filters.ApiKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApiKeyFilter apiKeyFilter) throws Exception {
        http.securityMatcher("/api/pets/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/api/pets").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/pets").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/pets/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/pets/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/pets/**").authenticated()
                        .anyRequest().denyAll()
                )
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class);
        //http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll())
                //.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
   /* public SecurityFilterChain securityFilterChain(HttpSecurity http, ApiKeyFilter apiKeyFilter) throws Exception {
        http
                .securityMatcher("/api/**")
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        return http.build();
    }*/
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
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
