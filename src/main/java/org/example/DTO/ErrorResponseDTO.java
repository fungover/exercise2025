package org.example.backend.DTO;

import java.time.OffsetDateTime;

public record ErrorResponseDTO(
        int status,
        String error,
        String message,
        OffsetDateTime timestamp
) {
    public ErrorResponseDTO(int status, String error, String message) {
        this(status, error, message, OffsetDateTime.now());
    }
}