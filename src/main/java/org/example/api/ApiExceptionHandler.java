package org.example.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice(basePackages = "org.example.api")
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        // Plocka ut ALLA fältfel och bygg en lista med "field message"
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + " " + f.getDefaultMessage())
                .toList();

        // Fallback om något gått snett och listan skulle vara tom
        if (errors.isEmpty()) {
            errors = List.of("Validation error");
        }

        // Returnera alla fel i en array under "errors"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("errors", errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Malformed request body"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConstraint(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Data conflict"));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleRse(ResponseStatusException ex) {
        var status = ex.getStatusCode();
        var msg = (ex.getReason() != null) ? ex.getReason() : status.toString();
        return ResponseEntity.status(status).body(Map.of("error", msg));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArg(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
}
