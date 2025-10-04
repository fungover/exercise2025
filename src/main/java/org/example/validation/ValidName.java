package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {})
@NotNull @Size(min=2, max=12) @NotBlank
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
  String message() default "Incorrected name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
