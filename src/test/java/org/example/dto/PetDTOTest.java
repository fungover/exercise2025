package org.example.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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
        PetDTO pet = new PetDTO("Bella", "Dog", 50, 50);
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet);

        assertThat(violations).isEmpty();
    }

    @Test
    void invalidBlankName() {
        PetDTO pet = new PetDTO("", "Dog", 50, 50);
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet);

        assertThat(violations).hasSize(1);
    }

    @Test
    void invalidTooLongName() {
        PetDTO pet = new PetDTO("a".repeat(60), "Dog", 50, 50);
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet);

        assertThat(violations).hasSize(1);
    }

    @Test
    void invalidBlankSpecies() {
        PetDTO pet = new PetDTO("Bella", "", 50, 50);
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet);

        assertThat(violations).hasSize(1);
    }

    @Test
    void invalidTooLongSpecies() {
        PetDTO pet = new PetDTO("Bella", "a".repeat(60), 50, 50);
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet);

        assertThat(violations).hasSize(1);
    }

    @Test
    void invalidHungerBelow0() {
        PetDTO pet = new PetDTO("Bella", "Dog", -5, 50);
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet);

        assertThat(violations).hasSize(1);
    }

    @Test
    void invalidHungerAbove100() {
        PetDTO pet = new PetDTO("Bella", "Dog", 150, 50);
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet);

        assertThat(violations).hasSize(1);
    }

    @Test
    void invalidHappinessBelow0() {
        PetDTO pet = new PetDTO("Bella", "Dog", 50, -1);
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet);

        assertThat(violations).hasSize(1);
    }

    @Test
    void invalidHappinessAbove100() {
        PetDTO pet = new PetDTO("Bella", "Dog", 50, 120);
        Set<ConstraintViolation<PetDTO>> violations = validator.validate(pet);

        assertThat(violations).hasSize(1);
    }
}
