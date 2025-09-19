package org.example.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.*;

class DiscountDecoratorTest {

    @Test
    @DisplayName("DiscountDecorator: returns discounted price")
    void getPrice() {
        Product testProduct = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.GENERAL)
                .rating(1)
                .price(100)
                .build();

        DiscountDecorator discountDecorator = new DiscountDecorator(testProduct, 10);
        assertThat(discountDecorator.getPrice()).isCloseTo(90.0, within(1e-6));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    @DisplayName("DiscountDecorator: throws IllegalArgumentException on invalid input")
    void throwsExceptionIfInvalidInputs(Sellable testProduct, double price, Class<? extends Throwable> expectedException) {
        assertThrows(expectedException, () -> new DiscountDecorator(testProduct, price));
    }

    static Stream<Arguments> invalidInputs() {
        Product validProduct = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.GENERAL)
                .rating(1)
                .price(100)
                .build();

        return Stream.of(
                Arguments.of(null, 10., NullPointerException.class),
                Arguments.of(validProduct, -5, IllegalArgumentException.class),
                Arguments.of(validProduct, 101, IllegalArgumentException.class)
        );
    }

}