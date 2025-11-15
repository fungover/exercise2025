package org.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.user.ApiPrincipal;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private  final JwtUtil jwtUtil;

  public ApiKeyFilter(UserRepository userRepository, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String path = request.getRequestURI();

    if (!path.startsWith("/api/")) {
      filterChain.doFilter(request, response);
      return;
    }

    String header = request.getHeader("Authorization");
    if (header == null || !header.startsWith("Bearer ")) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing JWT token");
      return;
    }

    String token = header.substring(7);

    if (!jwtUtil.validateJwtToken(token)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing JWT token");
      return;
    }

    String email = jwtUtil.getUsernameFromToken(token);

    if (email == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token does not contain user info");
      return;
    }

    UserEntity user = userRepository.findByEmail(email).orElse(null);

    if (user == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found for provided token");
      return;
    }

  ApiPrincipal apiPrincipal = new ApiPrincipal(user.getId(), user.getEmail());
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(apiPrincipal, null,
            AuthorityUtils.createAuthorityList("ROLE_API_USER"));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
