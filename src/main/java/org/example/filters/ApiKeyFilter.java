/*
package org.example.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {
	private static final String API_HEADER = "X-Api-Key";
	private static final String API_KEY = "very-secret-key";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String apiKey = request.getHeader(API_HEADER);
		if (apiKey.equals(API_KEY)) {
			Authentication auth = new UsernamePasswordAuthenticationToken("api_user", null,
							List.of(new SimpleGrantedAuthority("API_USER")));
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(auth);
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(401);
		}
	}
}
*/
