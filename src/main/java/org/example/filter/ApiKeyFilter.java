package org.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.ApiEntity;
import org.example.repository.ApiRepository;
import org.example.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final ApiRepository apiRepository;

  public ApiKeyFilter(UserRepository userRepository, ApiRepository apiRepository) {
    this.userRepository = userRepository;
    this.apiRepository = apiRepository;
  }

  @Override
  @Transactional
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String path = request.getRequestURI();

    if (!path.startsWith("/api/")) {
      filterChain.doFilter(request, response);
      return;
    }

    String apiKey = request.getHeader("X-API-KEY");
    if (apiKey == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API key");
      return;
    }

    ApiEntity apiEntity = apiRepository.findByApiKey(apiKey).orElse(null);

    if (apiEntity == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API key");
      return;
    }

    long currentCount = apiEntity.getCounter() == null ? 0 : apiEntity.getCounter();
    apiEntity.setCounter(currentCount + 1);
    apiEntity.setLastUsedAt(LocalDateTime.now());
    apiRepository.save(apiEntity);


    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("api_user", null,
            AuthorityUtils.createAuthorityList("ROLE_API_USER"));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
