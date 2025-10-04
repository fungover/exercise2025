package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {})
@NotNull @Min(0) @Max(10) @NotBlank
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHappiness {
  String message() default "Incorrected happiness";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
