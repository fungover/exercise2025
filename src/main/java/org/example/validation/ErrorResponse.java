package org.example.validation;

import java.util.List;

public record ErrorResponse(
        String message,
        int status) {
}
