package org.example.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.IdValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint (validatedBy = {IdValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidId {
  String message() default "Ogiltigt id";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
