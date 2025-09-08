package org.example.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    private String id;
    private String name;
    private Category category;
    private int rating;
    private Product product;

    @BeforeEach
    void setUp() {
        id = "1";
        name = "Test product";
        category = Category.ELECTRONICS;
        rating = 8;
        product = new Product(id, name, category, rating);
    }

    @Test
    void shouldCreateProductWithId() {
        assertEquals(id, product.id());
    }

    @Test
    void shouldCreateProductWithIdAndName() {
        assertEquals(id, product.id());
        assertEquals(name, product.name());
    }

    @Test
    void productShouldHaveCategory() {
        assertEquals(category, product.category());
    }

    @Test
    void productShouldHaveRating() {
        assertEquals(rating, product.rating());
    }

    // Using this test for Given, When, Then reminder
    @Test
    void shouldHaveProductWithAllFields() {
        // Given
        String id = "1";
        String name = "Test product";
        Category category = Category.ELECTRONICS;
        int rating = 8;
        // When
        Product product = new Product(id, name, category, rating);
        // Then
        assertEquals(id, product.id());
        assertEquals(name, product.name());
        assertEquals(category, product.category());
        assertEquals(rating, product.rating());
    }
}
