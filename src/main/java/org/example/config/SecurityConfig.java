package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.core.userdetails.User.withUsername;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                // API security filter
                // All endpoints except GET require ADMIN role
                .securityMatcher("/api/animals/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/animals/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/animals/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/animals/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/animals/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )


                .httpBasic(basic -> basic.authenticationEntryPoint(
                        (request, response, authException) -> {
                            response.setStatus(401);
                        })
        );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
                // Web Security Filter
                // Browsers can access login page form
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/css/**").permitAll()
                        .anyRequest().authenticated()
                )
//                .formLogin(Customizer.withDefaults());
                .formLogin(form ->
                        form.defaultSuccessUrl("/api/animals",true)
                                .failureUrl("/login?error")
                );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
        UserDetails user = withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER","ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user,admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
