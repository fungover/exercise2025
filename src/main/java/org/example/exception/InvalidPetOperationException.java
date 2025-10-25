package org.example.exception;

/**
 * Exception thrown when an invalid operation is performed on a pet.
 */
public class InvalidPetOperationException extends RuntimeException {

    private final String operation;

    public InvalidPetOperationException(String operation, String message) {
        super(message);
        this.operation = operation;
    }

    public InvalidPetOperationException(String message) {
        super(message);
        this.operation = "unknown";
    }

    public String getOperation() {
        return operation;
    }
}
