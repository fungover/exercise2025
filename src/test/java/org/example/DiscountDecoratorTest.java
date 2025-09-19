package org.example;

import org.example.entities.Category;
import org.example.entities.DiscountDecorator;
import org.example.entities.Product;
import org.example.entities.Sellable;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscountDecoratorTest {

    @Test
    void apply10PercentDiscount() {
        Product product = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(1000.0)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Sellable discounted = new DiscountDecorator(product, 10);

        assertEquals(900.0, discounted.getPrice(), 0.01);
        assertEquals("Laptop", discounted.getName());
    }

    @Test
    void discountDoesNotExceedPrice() {
        Product cheapProduct = new Product.Builder()
                .id("2")
                .name("Sticker")
                .category(Category.TOYS)
                .rating(5)
                .price(5.0)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Sellable freeProduct = new DiscountDecorator(cheapProduct, 100);

        assertTrue(freeProduct.getPrice() >= 0);
    }

    @Test
    void multipleDecoratorsApplyCorrectly() {
        Product product = new Product.Builder()
                .id("3")
                .name("Phone")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(500.0)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Sellable discounted = new DiscountDecorator(product, 10);
        discounted = new DiscountDecorator(discounted, 20);

        assertEquals(360.0, discounted.getPrice(), 0.01);
    }

    @Test
    void originalAttributesAreAccessible() {
        Product product = new Product.Builder()
                .id("4")
                .name("Book")
                .category(Category.BOOKS)
                .rating(7)
                .price(20.0)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Sellable discounted = new DiscountDecorator(product, 50);

        assertEquals("Book", discounted.getName());
        assertEquals(Category.BOOKS, ((Product) product).getCategory());
        assertEquals(7, ((Product) product).getRating());
    }
}
