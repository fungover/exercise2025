package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryProductRepositoryTest {
    private ProductService productService;

    @BeforeEach
    void setUp() {
        ProductRepository repository = new InMemoryProductRepository();
        productService = new ProductService(repository);

    }

    @Test
    @DisplayName("Should throw exception when adding product with duplicate ID")
    void addProductWithDuplicateIdThrowsException() {
        Product product1 = new Product.Builder()
                .id("1") // Same ID as product2
                .name("First Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .build();

        Product product2 = new Product.Builder()
                .id("1") // Duplicate ID
                .name("Second Product")
                .category(Category.BOOKS)
                .rating(7)
                .build();

        productService.addProduct(product1);

        IllegalArgumentException exception = assertThrows( // Expecting an exception
                IllegalArgumentException.class,
                () -> productService.addProduct(product2) // Attempt to add duplicate ID
        );
        assertEquals("Product with id 1 already exists", exception.getMessage()); // Check exception message
    }
}
