package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidSpeciesCheck implements ConstraintValidator<ValidSpecies, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("[a-zA-Z]+") && !value.isEmpty() &&
                value.length() <= 100;
    }
}
