package org.example.exceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import org.example.controller.NoteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.example.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
    final Logger log = LoggerFactory.getLogger(NoteController.class);

    log.error("Handling EntityNotFoundException: {}", ex.getMessage());

    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

}