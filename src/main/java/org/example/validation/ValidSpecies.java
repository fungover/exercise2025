package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.validators.SpeciesValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {SpeciesValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSpecies {
	String message() default "Invalid species";

	String[] allowed() default {"bird", "cat", "dog", "lizard", "rabbit", "hamster"};

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
