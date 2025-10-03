package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy={SwedishPersonalNumberValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSwedishPersonalNumber {
    String message() default "Ogiltigt personnummer";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
