package org.example.config;

import org.example.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.example.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // API-behörigheter
                        .requestMatchers(HttpMethod.DELETE, "/api/catches/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")

                        // Admin-gränssnitt
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Offentliga sidor - TILLÅT ALLA HTTP-METODER
                        .requestMatchers("/", "/login/**", "/register/**", "/error").permitAll()

                        // Allt annat kräver inloggning
                        .anyRequest().authenticated()
                )
                // Form-login för vanliga användare
                .formLogin(form -> form

                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                // HTTP Basic (för t.ex. Postman)
                .httpBasic(Customizer.withDefaults())

                // CSRF avstängt för API-anrop
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**", "/register/**")
                )
                // Logout-hantering
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Du kan välja styrka 10 som i din första config
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

/*    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {


        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("adminpassword"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }*/
}
//old code

/*package org.example.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.DELETE, "/api/catches/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/", "/login/**", "/register/**").permitAll()
                        .anyRequest().authenticated()
                )
                // the user will be redirected to /login if not logged in when accessing any page except default page
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                .httpBasic(Customizer.withDefaults()) // TODO: this is needed for postman requests read about it more
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {


        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("adminpassword"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/

//TODO: API-key for authentication when RestAPI is used
// Role based API

//TODO: User should only be able to do POST, PUT and GET
//TODO: Admin should use CRUD operations
//TODO: All should be able to do GET-requests

//TODO: We need to make everything thread safe
