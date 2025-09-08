package org.example.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {

    @Test
    void shouldHaveElectronicsCategory() {
        Category category = Category.ELECTRONICS;
        assertEquals("ELECTRONICS", category.name());
    }
}
