package org.example.pet;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PetDTOTest {
	@Test
	void testAdoptingANewPet() {
		Pet pet = new PetDTO("Fido", "dog");
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
			assertTrue(violations.isEmpty());
		}
	}

	@Test
	void testAdoptingAnInvalidPet() {
		Pet pet = new PetDTO("Fido", "Cow");
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
			assertThat(violations.size()).isEqualTo(1);
			assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid species");
		}
	}
}