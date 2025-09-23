package org.example.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountDecoratorTest {
    private Sellable product;
    private DiscountDecorator decoratedProduct;

    @Test
    void testDiscountedPriceWithProductPrice() {
        product = new Product.Builder()
                .id("123")
                .name("Fleece")
                .category(Category.CLOTHES)
                .rating(10)
                .price(BigDecimal.valueOf(100))
                .build();

        decoratedProduct = new DiscountDecorator(product,20);
        BigDecimal price = decoratedProduct.price();

        assertThat(price).isEqualTo(BigDecimal.valueOf(80).setScale(2,
                RoundingMode.HALF_UP));
    }
    @Test
    void testDiscountedPriceWithNoProductPrice() {
        product = new Product.Builder()
                .id("123")
                .name("Fleece")
                .category(Category.CLOTHES)
                .rating(10)
                .build();

        decoratedProduct = new DiscountDecorator(product,20);
        BigDecimal price = decoratedProduct.price();

        assertThat(price).isEqualTo(BigDecimal.ZERO);
    }
}
