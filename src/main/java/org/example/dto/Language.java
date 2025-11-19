package org.example.dto;

import jakarta.validation.constraints.NotEmpty;

public record Language(@NotEmpty(message = "Language must be inserted") String textLanguage) {
}
