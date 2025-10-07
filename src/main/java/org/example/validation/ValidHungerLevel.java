package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {})
@Min(value = 0, message = "Hunger level must be between 0 and 10")
@Max(value = 10, message = "Hunger level must be between 0 and 10")
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHungerLevel {
    String message() default "Hunger level must be between 0 and 10";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
