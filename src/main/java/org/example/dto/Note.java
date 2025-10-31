package org.example.dto;

import java.time.LocalDateTime;

public record Note(Long id, String value, Long userId, LocalDateTime createdAt) {
}
