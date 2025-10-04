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
@NotNull @Min(1) @Max(5)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHunger {
  String message() default "Uncorrected hunger";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
