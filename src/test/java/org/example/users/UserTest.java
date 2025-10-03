package org.example.users;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class UserTest {

    @Test
    public void validName() {
        User user = new User("Kalle",true, "111211-2343");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations =
                validator.validate(user);

        assertEquals(0, violations.size());
    }

    @Test
    public void invalidNameWithMoreThan10Characters() {
        User user = new User("KalleKalle2",true,"111211-2343");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations =
                validator.validate(user);

        assertEquals(1, violations.size());
    }
    

  
}