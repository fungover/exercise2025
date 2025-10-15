package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy={})
@NotNull
@Size(min=1, max=100)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSpecies {
    String message() default "Ogiltig art";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
