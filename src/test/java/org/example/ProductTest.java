package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    @DisplayName("Can create a Product with a unique ID")
    public void canCreateProductWithId() {
        Product product = new Product(1);
        assertThat(product.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Can create a Product with a name")
    public void canCreateProductWithName() {
        Product product = new Product(1, "Laptop");
        assertThat(product.getName()).isEqualTo("Laptop");
    }

    @Test
    @DisplayName("Can create a Product with a category")
    public void canCreateProductWithCategory() {
        Product product = new Product(1, "Laptop", Category.GENERAL);
        assertThat(product.getCategory()).isEqualTo(Category.GENERAL);
    }

    @Test
    @DisplayName("Can create a Product with a rating")
    public void canCreateProductWithRating() {
        Product product = new Product(1, "Laptop", Category.GENERAL, 1);
        assertThat(product.getRating()).isEqualTo(1);
    }

    @Test
    @DisplayName("Can create a Product with a created date")
    public void canCreateProductWithGetCreatedDate() {
        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDate currentDate = LocalDate.of(2025, 1, 1);
            mockedStatic.when(LocalDate::now).thenReturn(currentDate);

            Product product = new Product(1, "Laptop", Category.GENERAL, 1);
            assertThat(product.getCreatedDate()).isEqualTo(currentDate);
        }
    }

    @Test
    @DisplayName("Can update a Product's modified date when name is changed")
    public void canCreateProductWithModifiedDate() {
        Product product;
        Product updated;

        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDate currentDate = LocalDate.of(2025, 1, 1);
            mockedStatic.when(LocalDate::now).thenReturn(currentDate);
            product = new Product(1, "Laptop", Category.GENERAL, 1);
        }

        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDate updateDate = LocalDate.of(2025, 1, 5);
            mockedStatic.when(LocalDate::now).thenReturn(updateDate);
            updated = product.update("Notebook", Category.GENERAL, 2);

            assertThat(updated.getModifiedDate()).isEqualTo(updateDate);
            assertThat(product.getModifiedDate()).isNotEqualTo(updateDate);
        }
    }

}
