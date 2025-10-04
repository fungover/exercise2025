package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {})
@NotNull @Min(1) @Max(5) @NotBlank
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHunger {
  String message() default "Uncorrected hunger";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
