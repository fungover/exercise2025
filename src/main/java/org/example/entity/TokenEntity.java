package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class TokenEntity {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false, unique = true)
  private String refreshToken;

  @Column(nullable = false)
  private Long counter;

  private LocalDateTime lastUsedAt;

  public TokenEntity() {
  }

  public TokenEntity(Long id, UserEntity user, String token, String refreshToken, Long counter, LocalDateTime lastUsedAt) {
    this.id = id;
    this.user = user;
    this.token = token;
    this.refreshToken = refreshToken;
    this.counter = counter;
    this.lastUsedAt = lastUsedAt;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;

  }

  public Long getCounter() {
    return counter;
  }

  public void setCounter(Long counter) {
    this.counter = counter;
  }

  public LocalDateTime getLastUsedAt() {
    return lastUsedAt;
  }

  public void setLastUsedAt(LocalDateTime lastUsedAt) {
    this.lastUsedAt = lastUsedAt;
  }
}
