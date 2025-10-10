package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        ProductRepository repository = new InMemoryProductRepository();
        productService = new ProductService(repository);

        sampleProduct = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.of(2023, 1, 1))
                .modifiedDate(LocalDate.of(2023, 1, 1))
                .build();

        productService.addProduct(sampleProduct);
    }

    @Test
    void testAddProductSuccess() {
        Product newProduct = new Product.Builder()
                .id("2")
                .name("Book")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        productService.addProduct(newProduct);
        assertEquals(newProduct, productService.getProductById("2"));
    }

    @Test
    void testAddProductWithEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Product invalidProduct = new Product.Builder()
                    .id("3")
                    .name("") // <-- felet kastas här
                    .category(Category.BOOKS)
                    .rating(5)
                    .build(); // <-- exception kastas vid build()

            productService.addProduct(invalidProduct); // <-- nås aldrig
        });
    }

    @Test
    void testUpdateProductSuccess() {
        productService.updateProduct("1", "Gaming Laptop", Category.ELECTRONICS, 9);
        Product updated = productService.getProductById("1");

        assertEquals("Gaming Laptop", updated.getName());
        assertEquals(9, updated.getRating());
        assertNotEquals(updated.getCreatedDate(), updated.getModifiedDate());
    }

    @Test
    void testUpdateNonexistentProductThrowsException() {
        assertThrows(NoSuchElementException.class, () ->
                productService.updateProduct("999", "Name", Category.FOOD, 5)
        );
    }

    @Test
    void testGetAllProducts() {
        List<Product> all = productService.getAllProducts();
        assertEquals(1, all.size());
        assertTrue(all.contains(sampleProduct));
    }

    @Test
    void testGetProductByIdSuccess() {
        Product found = productService.getProductById("1");
        assertEquals(sampleProduct, found);
    }

    @Test
    void testGetProductByIdFailure() {
        assertThrows(NoSuchElementException.class, () -> productService.getProductById("999"));
    }

    @Test
    void testGetProductsByCategorySorted() {
        Product second = new Product.Builder()
                .id("2")
                .name("Camera")
                .category(Category.ELECTRONICS)
                .rating(6)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        productService.addProduct(second);
        List<Product> electronics = productService.getProductsByCategorySorted(Category.ELECTRONICS);

        assertEquals(2, electronics.size());
        assertEquals("Camera", electronics.get(0).getName());
    }

    @Test
    void testGetProductsCreatedAfter() {
        Product recent = new Product.Builder()
                .id("3")
                .name("Tablet")
                .category(Category.ELECTRONICS)
                .rating(6)
                .createdDate(LocalDate.of(2025, 1, 1))
                .modifiedDate(LocalDate.of(2025, 1, 1))
                .build();

        productService.addProduct(recent);

        List<Product> recentProducts = productService.getProductsCreatedAfter(LocalDate.of(2024, 12, 31));
        assertEquals(1, recentProducts.size());
        assertEquals("Tablet", recentProducts.get(0).getName());
    }

    @Test
    void testGetModifiedProducts() {
        productService.updateProduct("1", "Updated Laptop", Category.ELECTRONICS, 9);
        List<Product> modified = productService.getModifiedProducts();

        assertEquals(1, modified.size());
        assertEquals("Updated Laptop", modified.get(0).getName());
    }
}
