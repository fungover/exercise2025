package org.example.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PetQueryParamsTest {

    private static Validator validator;

    @BeforeAll
    static void setValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void validParams() {
        PetQueryParams params = new PetQueryParams();
        params.setOffset(0);
        params.setLimit(10);
        params.setSpecies("Dog");
        params.setSortBy("name");
        params.setOrder("asc");

        var violations = validator.validate(params);
        assertThat(violations).isEmpty();
    }

    @Test
    void invalidOffsetBelow0() {
        PetQueryParams params = new PetQueryParams();
        params.setOffset(-1); // Invalid offset
        params.setLimit(10);

        var violations = validator.validate(params);
        assertThat(violations).hasSize(1); // Expecting one violation for offset
    }

    @Test
    void invalidLimitBelow1() {
        PetQueryParams params = new PetQueryParams();
        params.setOffset(0);
        params.setLimit(0); // Invalid limit

        var violations = validator.validate(params);
        assertThat(violations).hasSize(1); // Expecting one violation for limit
    }

}
