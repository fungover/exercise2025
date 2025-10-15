package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = ValidNameCheck.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "Namnet måste innehålla 2-10 bokstäver och inga specialtecken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
