package org.example.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.pet.Pet;
import org.example.validation.ValidSpecies;

import java.util.ArrayList;
import java.util.List;

public class SpeciesValidator implements ConstraintValidator<ValidSpecies, String> {
	private final List<String> validSpecies = new ArrayList<String>();
	{
		validSpecies.add("bird");
		validSpecies.add("cat");
		validSpecies.add("dog");
		validSpecies.add("lizard");
		validSpecies.add("rabbit");
		validSpecies.add("hamster");
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		for (String species : validSpecies) {
			if (species.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
}
