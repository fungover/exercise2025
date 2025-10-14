package org.example.validation.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.validation.ValidSpecies;

import java.util.*;
import java.util.stream.Collectors;

public class SpeciesValidator implements ConstraintValidator<ValidSpecies, String> {
	private Set<String> allowed;

	@Override
	public void initialize(ValidSpecies ann) {
		allowed = Arrays.stream(ann.allowed())
						.map(s -> s.toLowerCase(Locale.ROOT).trim())
						.collect(Collectors.toSet());
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		if (s == null) return false;
		return allowed.contains(s.toLowerCase(Locale.ROOT).trim());
	}
}
