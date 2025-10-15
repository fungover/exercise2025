package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = ValidSpeciesCheck.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSpecies {
    String message() default "Arten måste innehålla 1-100 bokstäver och inga specialtecken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
