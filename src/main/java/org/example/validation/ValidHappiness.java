package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {})
@NotNull @Min(0) @Max(10)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHappiness {
  String message() default "Uncorrected happiness";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
