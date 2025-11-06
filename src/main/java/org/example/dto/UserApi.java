package org.example.dto;

import java.time.LocalDateTime;

public record UserApi(String apiKey, Long counter, LocalDateTime lastUsedAt) {
}
