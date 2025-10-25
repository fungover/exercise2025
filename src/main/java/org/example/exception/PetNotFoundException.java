package org.example.exception;

/**
 * Exception thrown when a pet with the specified ID cannot be found.
 */
public class PetNotFoundException extends RuntimeException {

    private final Long petId;

    public PetNotFoundException(Long petId) {
        super("Pet with ID " + petId + " not found");
        this.petId = petId;
    }

    public PetNotFoundException(Long petId, String message) {
        super(message);
        this.petId = petId;
    }

    public Long getPetId() {
        return petId;
    }
}
