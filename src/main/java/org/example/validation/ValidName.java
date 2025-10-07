package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {})
@NotBlank(message = "Name cannot be blank")
@Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "Name must be between 1 and 20 characters";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
