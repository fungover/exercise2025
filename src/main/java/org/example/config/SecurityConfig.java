package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

	@Bean
	public SecurityFilterChain web(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		http
						.formLogin(formLogin -> formLogin.defaultSuccessUrl("/", true).loginPage("/login"))
						.logout(logout -> {
							logout.logoutSuccessUrl("/");
						})
						.authorizeHttpRequests((authorize) -> authorize
										.requestMatchers("/").permitAll()
										.requestMatchers("**.css").permitAll()
										.requestMatchers("/login").permitAll()
										.requestMatchers("/api/admissions/new").hasRole("ADMIN")
										.requestMatchers("/api/**").authenticated()
										.requestMatchers("/logout").authenticated()
										.anyRequest().authenticated());
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails admin = User.withUsername("admin")
						.password(passwordEncoder.encode("password"))
						.roles("ADMIN", "DEV", "USER")
						.build();
		return new InMemoryUserDetailsManager(admin);
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
