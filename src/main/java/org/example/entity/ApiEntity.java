package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_api")
public class ApiEntity {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private UserEntity user;

  @Column(nullable = false, unique = true, updatable = false)
  private String apiKey;

  @Column(nullable = false)
  private Long counter;

  private LocalDateTime lastUsedAt;

  @PrePersist
  public void generateApiKey() {
    if (this.apiKey == null) {
      this.apiKey = UUID.randomUUID().toString();
    }
  }

  public ApiEntity() {
  }

  public ApiEntity(Long id, UserEntity user, String apiKey, Long counter, LocalDateTime lastUsedAt) {
    this.id = id;
    this.user = user;
    this.apiKey = apiKey;
    this.counter = counter;
    this.lastUsedAt = lastUsedAt;
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

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
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
