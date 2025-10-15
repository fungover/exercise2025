package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = ValidLevelCheck.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLevel {
    String message() default "Nummer måste vara mellan 1-10";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
