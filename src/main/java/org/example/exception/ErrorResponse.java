package org.example.exception;

import java.util.List;

public record ErrorResponse(
        String type,
        List<ErrorDetail> details
) {
    public ErrorResponse(String type, List<ErrorDetail> details) {
        this.type = type;
        this.details = details != null ? List.copyOf(details) : List.of();
    }
}
