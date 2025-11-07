package org.example.dto;

import java.time.LocalDateTime;

public record NoteNew(Long id, String value, LocalDateTime createdAt) {
}
