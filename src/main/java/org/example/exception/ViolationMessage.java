package org.example.exception;

public record ViolationMessage(
        String field,
        String message
) implements ErrorDetail {
}
