package org.example.exceptionHandler;

public class ConflictException extends RuntimeException {

  public ConflictException(String email) {
    super("User with email " + email + " already exists");
  }
}