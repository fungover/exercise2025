package org.example.service.decorator;

import org.example.decorators.DiscountDecorator;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.Sellable;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DiscountDecoratorTest {

    // --- Should apply discount correctly ---
    @Test
    void discountDecoratorShouldApplyDiscountCorrectly() {
        Sellable product = new Product.Builder()
                .id("1")
                .name("Kiwi")
                .category(Category.Food)
                .price(10)
                .rating(9)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();
        Sellable discounted = new DiscountDecorator(product, 1.5);

        assertEquals(10, product.getPrice()); // Original price
        assertEquals(9.85, discounted.getPrice()); // Discounted price
        assertEquals(product.getName(), discounted.getName()); // name unchanged
        assertEquals(product.getId(), discounted.getId()); // id unchanged
    }
    // --- 0% discount ---
    @Test
    void discountDecoratorShouldApplyZeroDiscountCorrectly() {
        Sellable product = new Product.Builder()
                .id("2")
                .name("Kiwi")
                .category(Category.Food)
                .price(10)
                .rating(9)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();
        Sellable discounted = new DiscountDecorator(product, 0);
        assertEquals(10, discounted.getPrice(), 0.0001);

    }
    // --- 100% discount ---
    @Test
    void hundredPercentDiscountShouldApplyCorrectly() {
        Sellable product = new Product.Builder()
                .id("3")
                .name("Kiwi")
                .category(Category.Food)
                .price(10)
                .rating(9)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();
        Sellable discounted = new DiscountDecorator(product, 100);

        assertEquals(0.0, discounted.getPrice(), 0.0001);
    }
    // --- IllegalArgumentException ---
    @Test
    void invalidDiscountPercentageShouldThrowException() {
        Sellable product = new Product.Builder()
                .id("4")
                .name("Banana")
                .category(Category.Food)
                .price(7)
                .rating(4)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> new DiscountDecorator(product, -5));

        assertThrows(IllegalArgumentException.class,
                () -> new DiscountDecorator(product, 150));
    }
}
