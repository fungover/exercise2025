package org.example.decorator;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.Sellable;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DiscountDecoratorTest {

    @Test
    void shouldApplyDiscountCorrectly() {
        Product laptop = Product.builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(5)
                .price(BigDecimal.valueOf(1000.00))
                .build();

        Sellable discountedLaptop = new DiscountDecorator(laptop, 20);

        assertEquals("1", discountedLaptop.getId());
        assertEquals("Laptop", discountedLaptop.getName());
        assertEquals(new BigDecimal("800.00"), discountedLaptop.getPrice());
    }

    @Test
    void shouldThrowExceptionForInvalidDiscount() {
        Product product = Product.builder()
                .id("1")
                .name("Test")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(BigDecimal.valueOf(100.00))
                .build();

        assertThrows(IllegalArgumentException.class, () ->
                new DiscountDecorator(product, -5));

        assertThrows(IllegalArgumentException.class, () ->
                new DiscountDecorator(product, 105));
    }

    @Test
    void shouldCalculateDiscountForDifferentPrices() {
        Product product = Product.builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(5)
                .price(new BigDecimal("20.00"))
                .build();

        Product anotherProduct = Product.builder()
                .id("2")
                .name("Test Product")
                .category(Category.SPORTS)
                .rating(6)
                .price(new BigDecimal("10.00"))
                .build();

        Sellable discounted = new DiscountDecorator(product, 50);
        Sellable anotherDiscounted = new DiscountDecorator(anotherProduct, 90);

        assertEquals(new BigDecimal("10.00"), discounted.getPrice());
        assertEquals(new BigDecimal("1.00"), anotherDiscounted.getPrice());
    }

    @Test
    void shouldCalculateDiscountForProductsWithDecimals() {
        Product product = Product.builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(5)
                .price(new BigDecimal("20.50"))
                .build();

        Product anotherProduct = Product.builder()
                .id("2")
                .name("Test Product")
                .category(Category.CLOTHING)
                .rating(2)
                .price(new BigDecimal("19.99"))
                .build();

        Sellable discounted = new DiscountDecorator(product, 50);
        Sellable anotherDiscounted = new DiscountDecorator(anotherProduct, 10);

        assertEquals(new BigDecimal("10.25"), discounted.getPrice());
        assertEquals(new BigDecimal("17.99"), anotherDiscounted.getPrice());
    }

    @Test
    void shouldHandleZeroDiscount() {
        Product product = Product.builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(5)
                .price(new BigDecimal("49.99"))
                .build();

        Sellable discounted = new DiscountDecorator(product, 0);

        assertEquals(new BigDecimal("49.99"), discounted.getPrice());
    }

    @Test
    void shouldHandleMaxDiscount() {
        Product product = Product.builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(5)
                .price(new BigDecimal("49.99"))
                .build();

        Sellable discounted = new DiscountDecorator(product, 100);

        assertEquals(new BigDecimal("0.00"), discounted.getPrice());
    }

}