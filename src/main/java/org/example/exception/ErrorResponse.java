package org.example.exception;

import java.util.List;
import java.util.Objects;

public record ErrorResponse(
        String type,
        List<ErrorDetail> details
) {
    public ErrorResponse(String type, List<ErrorDetail> details) {
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.details = details != null ? List.copyOf(details) : List.of();
    }
}
