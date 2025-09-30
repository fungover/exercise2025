package org.example.dto;

public class PetResponseDTO {
    private String message;
    private PetDTO pet;

    // No-args constructor
    public PetResponseDTO(){}

    // All-args constructor
    public PetResponseDTO(String message) {
        this.message = message;
    }

    public PetResponseDTO(String message, PetDTO pet) {
        this.message = message;
        this.pet = pet;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public PetDTO getPet() {
        return pet;
    }

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }
    public void setPet(PetDTO pet) {
        this.pet = pet;
    }
}
