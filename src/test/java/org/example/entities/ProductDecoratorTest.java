package org.example.entities;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ProductDecoratorTest {

    @Test
    void constructor_NullProduct_ThrowsNullPointerException() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new TestProductDecorator(null);
        });
        assertEquals("decoratedProduct cannot be null", exception.getMessage());
    }

    @Test
    void delegatesToDecoratedProduct() {
        // Given
        Product product = new Product.Builder()
            .id("1")
            .name("Test Product")
            .category(Category.ELECTRONICS)
            .rating(5)
            .price(new BigDecimal("99.99"))
            .build();

        // When
        TestProductDecorator decorator = new TestProductDecorator(product);

        // Then
        assertEquals("1", decorator.getId());
        assertEquals("Test Product", decorator.getName());
        assertEquals(new BigDecimal("99.99"), decorator.getPrice());
    }

    // Concrete implementation of ProductDecorator for testing
    private static class TestProductDecorator extends ProductDecorator {
        public TestProductDecorator(Sellable decoratedProduct) {
            super(decoratedProduct);
        }
    }
}