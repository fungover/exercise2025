package org.example.exceptions;


// Exception for when a requested pet is not found - HTTP 404 Not Found
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
