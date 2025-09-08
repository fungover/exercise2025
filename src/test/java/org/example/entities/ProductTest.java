package org.example.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void shouldCreateProductWithId() {
        // Given
        String id = "1";
        String name = "Test product";
        Category category = Category.ELECTRONICS;
        int rating = 8;
        // When
        Product product = new Product(id, name, category, rating);
        // Then
        assertEquals(id, product.id());

    }

    @Test
    void shouldCreateProductWithIdAndName() {
        String id = "1";
        String name = "Test product";
        Category category = Category.ELECTRONICS;
        int rating = 8;

        Product product = new Product(id, name, category, rating);

        assertEquals(id, product.id());
        assertEquals(name, product.name());
    }

    @Test
    void productShouldHaveCategory() {
        String id = "1";
        String name = "Test product";
        Category category = Category.ELECTRONICS;
        int rating = 8;

        Product product = new Product(id, name, category, rating);

        assertEquals(category, product.category());
    }

    @Test
    void productShouldHaveRating() {
        String id = "1";
        String name = "Test product";
        Category category = Category.ELECTRONICS;
        int rating = 8;

        Product product = new Product(id, name, category, rating);

        assertEquals(rating, product.rating());
    }
}
