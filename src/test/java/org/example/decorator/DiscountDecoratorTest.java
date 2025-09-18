package org.example.decorator;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.Sellable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountDecoratorTest {

    @Test
    void discount_appliesPercentage_withoutChangingOriginal() {
        Product laptop = Product.builder()
                .id("id1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(1000.0)
                .build();

        Sellable discountedLaptop = new DiscountDecorator(laptop, 20.0);

        assertEquals(1000.0, laptop.getPrice(), 1e-6);
        assertEquals(800.0, discountedLaptop.getPrice(), 1e-6);
        assertEquals("Laptop", discountedLaptop.getName());
        assertEquals("id1", discountedLaptop.getId());
    }

    @Test
    void discount_outOfRange_throws() {
        Product product = Product.builder()
                .id("id1")
                .name("toy")
                .category(Category.TOYS)
                .rating(5)
                .price(100.0)
                .build();

        assertThrows(IllegalArgumentException.class, () -> new DiscountDecorator(product, -1));
        assertThrows(IllegalArgumentException.class, () -> new DiscountDecorator(product, 101));
    }

    @Test
    void zeroAndHundredPercentEdgeCases() {
        Product product = Product.builder()
                .id("id1")
                .name("electronic")
                .category(Category.ELECTRONICS)
                .rating(7)
                .price(200.0)
                .build();

        Sellable zero = new DiscountDecorator(product, 0);
        Sellable full = new DiscountDecorator(product, 100);

        assertEquals(200.0, zero.getPrice(), 1e-6);
        assertEquals(0.0, full.getPrice(), 1e-6);
    }
}
