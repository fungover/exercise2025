package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.token.UpdatedToken;
import org.example.repository.TokenRepository;
import org.example.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TokenService {
  private final TokenRepository tokenRepository;
  private final JwtUtil jwtUtil;

  public TokenService(TokenRepository tokenRepository, JwtUtil jwtUtil) {
    this.tokenRepository = tokenRepository;
    this.jwtUtil = jwtUtil;
  }

  @Transactional
  public UpdatedToken updateToken(String token, String refreshToken) {
    var tokenEntity = tokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new EntityNotFoundException("Refresh token not found"));

    if(!tokenEntity.getToken().equals(token)){
      throw new EntityNotFoundException("The token does not match the refresh token");
    }

    if (!jwtUtil.validateJwtToken(refreshToken)) {
      throw new EntityNotFoundException("Invalid refresh token");
    }

    String newAccessToken = jwtUtil.generateToken(tokenEntity.getUser().getEmail(), 1000 * 60 * 5L);

    tokenEntity.setToken(newAccessToken);
    tokenEntity.setLastUsedAt(LocalDateTime.now());
    tokenRepository.save(tokenEntity);

    return new UpdatedToken(newAccessToken);
  }
}
