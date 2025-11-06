package org.example.repository;

import org.example.entity.ApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiRepository extends JpaRepository<ApiEntity, Long> {
  Optional<ApiEntity> findByApiKey(String apiKey);
}