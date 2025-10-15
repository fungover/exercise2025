package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy={})
@NotNull
@Size(min=2, max=10)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "Ogiltigt namn";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
