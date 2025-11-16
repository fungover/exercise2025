package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
    @Order(1)
    public SecurityFilterChain apifilterChain(
      HttpSecurity http) throws Exception {
        http//for API simplicity, if i want forms i have to enable
            .securityMatcher(
              "/api/**")// restrict this filter chain to api/**
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(
              auth -> auth.requestMatchers(HttpMethod.GET,
                                           "/api/animals/**")
                          .permitAll() // allow anyone to GET /api/animals
                          .requestMatchers("/api/**")
                          // requires Admin role for all post,put,
                          // delete for /api/**
                          .hasRole("ADMIN")
                          .anyRequest()
                          .authenticated())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain formLoginSecurityFilterChain(
      HttpSecurity http) throws Exception {
        //this one picks up everything that isn't /api/**
        http.authorizeHttpRequests(
              auth -> auth.requestMatchers("/login/**")
                          .permitAll() //allow anyone to access login
                          .requestMatchers("animals/add")
                          .hasRole("ADMIN")
                          .anyRequest()
                          .authenticated())
            .formLogin(form -> form.loginPage("/login")
                                   .defaultSuccessUrl("/animals", true)
                                   .permitAll())
            .logout(logout -> logout.logoutUrl("/logout")
                                    .logoutSuccessUrl("/login?logout=true")
                                    .permitAll());
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
