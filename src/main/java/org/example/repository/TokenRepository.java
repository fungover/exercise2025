package org.example.repository;

import org.example.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
  Optional<TokenEntity> findByRefreshToken(String refreshToken);
  Optional<TokenEntity> findByToken(String token);
}