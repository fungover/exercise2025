package org.example.dto.note;

import java.time.LocalDateTime;

public record NoteNew(Long id, String value, LocalDateTime createdAt) {
}
