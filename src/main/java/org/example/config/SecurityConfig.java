package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

/*
	@Bean
	@Order(1)
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		http
						.authenticationManager(authenticationManager)
						.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
						.formLogin(formLogin -> {
							formLogin.defaultSuccessUrl("/", true);
							formLogin.loginPage("/login").permitAll();
						});
		return http.build();
	}
*/

	@Bean
	public SecurityFilterChain web(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		http
						.authenticationManager(authenticationManager)
						.formLogin(formLogin -> formLogin.defaultSuccessUrl("/", true).loginPage("/login"))
						.logout(logout -> {
							logout.logoutSuccessUrl("/");
						})
						.authorizeHttpRequests((authorize) -> authorize
										.requestMatchers("/login").permitAll()
										.requestMatchers("/").permitAll()
										.requestMatchers("**.css").permitAll()
										.requestMatchers("/logout").authenticated()
										.requestMatchers("/api/admissions/new").hasRole("ADMIN")
										.anyRequest().authenticated());
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails user =
						User.withUsername("user")
										.password(passwordEncoder.encode("password"))
										.roles("USER")
										.build();
		UserDetails admin = User.withUsername("admin")
						.password(passwordEncoder.encode("password"))
						.roles("ADMIN", "DEV")
						.build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		return authentication -> {
			String username = authentication.getName();
			String rawPassword = authentication.getCredentials().toString();

			UserDetails user = userDetailsService.loadUserByUsername(username);

			if (passwordEncoder.matches(rawPassword, user.getPassword())) {
				return new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities());
			}
			throw new BadCredentialsException("Bad credentials");
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
