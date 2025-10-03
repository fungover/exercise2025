package org.example.pets;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PetsTest {

//  @Test
//  public void addPet() {
//    Pets pets = new Pets("Cat", "12-12");
//    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//    Validator validator = factory.getValidator();
//    Set<ConstraintViolation<Pets>> violations = validator.validate(pets);
//    assertEquals(1, violations.size());
//    assertEquals("Pet already exists", violations.iterator().next().getMessage());
  }
