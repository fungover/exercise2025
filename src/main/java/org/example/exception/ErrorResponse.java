package org.example.exception;

import java.util.List;

public record ErrorResponse(
        String type,
        List<?> details
) {
}
