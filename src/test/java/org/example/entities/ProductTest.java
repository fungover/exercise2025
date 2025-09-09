package org.example.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

public class ProductTest {

    @Test
    void creatingProduct_withValidData_setsAllFields() {
        // Skapar testdata
        String id = "p-001";
        String name = "Samsung 55\" 4K";
        Category category = Category.TV;
        int rating = 8;
        LocalDate created = LocalDate.now();

        // Skapar produkt
        Product product = new Product(id, name, category, rating, created, created);

        // Kontrollerar att produktens fält är satta korrekt
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getRating()).isEqualTo(rating);
        assertThat(product.getCreatedDate()).isEqualTo(created);
        assertThat(product.getModifiedDate()).isEqualTo(created);
    }
}
