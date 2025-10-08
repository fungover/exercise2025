package org.example.validation;


import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {})
@NotBlank(message = "Species cannot be blank")
@Size(min = 1, max = 20)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSpecies {
    String message() default "Species must be between 1 and 20 characters";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
