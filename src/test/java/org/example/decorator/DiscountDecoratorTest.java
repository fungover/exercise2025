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

    @Test
    void discountDecoratorShouldThrowExceptionForNullProduct() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new DiscountDecorator(null, 20.0) // Null product
        );
        assertEquals("decoratedProduct cannot be null", exception.getMessage()); // Check exception message
    }

    @Test
    void discountDecoratorShouldHandleZeroPercentDiscount() {
        Product product = new Product.Builder()
                .id("test-1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(100.0)
                .build();

        Sellable noDiscount = new DiscountDecorator(product, 0.0); // 0% discount

        assertEquals(100.0, noDiscount.getPrice(), 0.01); // Price should be unchanged
    }

    @Test
    void discountDecoratorShouldHandleOneHundredPercentDiscount() {
        Product product = new Product.Builder()
                .id("test-2")
                .name("Free Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(100.0)
                .build();

        Sellable freeProduct = new DiscountDecorator(product, 100.0); // 100% discount

        assertEquals(0.0, freeProduct.getPrice(), 0.01); // Price should be zero
    }

}
