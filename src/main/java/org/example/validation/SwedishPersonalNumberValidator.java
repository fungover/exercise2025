package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SwedishPersonalNumberValidator implements ConstraintValidator<ValidSwedishPersonalNumber, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if( s == null)
            return false;
        if(  !s.matches("\\d{6,8}[-+]?\\d{4}"))
            return false;
        //Luhn check
        return true;
    }
}
