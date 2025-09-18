package org.example.decorator;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountDecoratorTest {

    @Test
    void discountDecoratorShouldApplyCorrectDiscount() {
        Product product = new Product.Builder()
                .id("test-1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(100.0)
                .build();

        Sellable discounted = new DiscountDecorator(product, 20.0); // 20% discount

        assertEquals(80.0, discounted.getPrice(), 0.01); // Allowing a small delta for floating point comparison
        assertEquals("Test Product", discounted.getName()); // Ensure name is unchanged
        assertEquals("test-1", discounted.getId()); // Ensure ID is unchanged
    }

    @Test
    void stackedDecoratorsShouldWork() {
        Product product = new Product.Builder()
                .id("test-2")
                .name("Stackable Product")
                .category(Category.BOOKS)
                .rating(9)
                .price(200.0)
                .build();

        Sellable firstDiscount = new DiscountDecorator(product, 10.0); // 10% discount -> 180.0
        Sellable secondDiscount = new DiscountDecorator(firstDiscount, 25.0); // 25% discount on 180.0 -> 135.0

        assertEquals(135.0, secondDiscount.getPrice(), 0.01);
    }

    @Test
    void discountDecoratorShouldThrowExceptionForNegativeDiscount() {
        Product product = new Product.Builder()
                .id("test-1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(100.0)
                .build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DiscountDecorator(product, -10.0) // Negative discount
        );
        assertEquals("Discount percentage must be between 0 and 100", exception.getMessage()); // Check exception message
    }

    @Test
    void discountDecoratorShouldThrowExceptionForDiscountOver100() {
        Product product = new Product.Builder()
                .id("test-1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(100.0)
                .build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DiscountDecorator(product, 150.0) // Discount over 100%
        );
        assertEquals("Discount percentage must be between 0 and 100", exception.getMessage()); // Check exception message
    }

}
