package org.example.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {

    @Test
    void shouldHaveElectronicsCategory() {
        Category category = Category.ELECTRONICS;
        assertEquals("ELECTRONICS", category.name());
    }

    @Test
    void shouldHaveSportsCategory() {
        Category category = Category.SPORTS;
        assertEquals("SPORTS", category.name());
    }

    @Test
    void shouldHaveClothingCategory() {
        Category category = Category.CLOTHING;
        assertEquals("CLOTHING", category.name());
    }
}
