package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {})
@NotNull @Min(1) @Max(3)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAmount {
  String message() default "Uncorrected amount";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
