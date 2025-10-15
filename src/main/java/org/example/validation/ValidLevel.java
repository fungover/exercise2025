package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy={})
@NotNull
@Min(value=1)
@Max(value=10)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLevel {
    String message() default "Nummer måste vara mellan 1-10";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
