package org.example.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles bad request exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        System.out.println(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    // Handles not found exceptions
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFoundException(NoSuchElementException e) {
        System.out.println(e.getMessage());
        return ResponseEntity
                .notFound()
                .build();
    }

}
