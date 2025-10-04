package org.example.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PetDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void validPet() {
        PetDTO pet = new PetDTO("Doris", "Dog", 50, 70); // Valid pet
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet); // Validate the pet
        assertEquals(0, violations.size()); // Expect no violations
    }

    @Test
    void invalidBlankName() {
        PetDTO pet = new PetDTO("", "Dog", 50, 50); // Invalid: blank name
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet); // Validate the pet
        assertEquals(1, violations.size()); // Expect 1 violation
    }
}
