package org.example.exceptions;

// Exception for when a request is invalid - HTTP 400 Bad Request
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
