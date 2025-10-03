package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.annotations.ValidId;

public class IdValidator implements ConstraintValidator<ValidId,String> {
  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//    if (s == null || !s.matches("\\d{2}-?\\d{2}"))
    return s != null && !s.isEmpty();
  }

}
