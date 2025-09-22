package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProductServiceTest {
    private final InMemoryProductRepository productRepository = new InMemoryProductRepository();
    private final ProductService productService = new ProductService(productRepository);

    @Test
    public void testGettingProductsByCategorySorted() {
        // Sorted by name A-Z.
        Product pants =
                returnNewProduct("23", "Pants", Category.CLOTHES, 9);
        Product shirt =
                returnNewProduct("24", "Shirt", Category.CLOTHES, 8);
        Product hat =
                returnNewProduct("25", "Hat", Category.CLOTHES, 3);

        productRepository.addProduct(pants); // index 0
        productRepository.addProduct(shirt); // index 1
        productRepository.addProduct(hat); // index 2

        List<Product> sortedList = new ArrayList<>();
        sortedList.add(0, hat);
        sortedList.add(1, pants);
        sortedList.add(2, shirt);

        assertEquals(sortedList,
                productService.getProductsByCategorySorted(Category.CLOTHES));
    }

    @Test
    public void testCannotFindProductsByCategory() {
        Product raisins =
                returnNewProduct("53", "Raisins", Category.PROVISIONS, 7);
        Product banana =
                returnNewProduct("54", "Banana", Category.PROVISIONS, 8);

        productRepository.addProduct(raisins); // index 0
        productRepository.addProduct(banana); // index 1

        List<Product> sortedList = new ArrayList<>();
        sortedList.add(0, banana);
        sortedList.add(1, raisins);

        // We are trying to sort clothes, but only have provisions in our products.
        assertNotEquals(sortedList,
                productService.getProductsByCategorySorted(Category.CLOTHES));
    }

    @Test
    public void testGetProductsCreatedAfterSpecifiedDate() {
        addNewProductToWarehouse("53", "Raisins", Category.PROVISIONS, 7);

        LocalDateTime beforeProduct = LocalDateTime.of(2025,
                9, 10, 11, 59);

        assertThat(productService.getProductsCreatedAfter(beforeProduct))
                .isEqualTo(productRepository.getAllProducts());
    }

    @Test
    public void testNoProductsCreatedAfterSpecifiedDate() {
        addNewProductToWarehouse("53", "Raisins", Category.PROVISIONS, 7);

        LocalDateTime afterProduct = LocalDateTime.of(2030,
                9, 10, 12, 1);

        assertThat(productService.getProductsCreatedAfter(afterProduct)).isEmpty();
    }

    @Test
    public void testGettingModifiedProducts() {
        addNewProductToWarehouse("53", "Raisins",
                Category.PROVISIONS, 7);
        Product banana = returnNewProduct("53", "Banana",
                Category.PROVISIONS, 8);

        productRepository.updateProduct(banana);

        assertThat(productService.getModifiedProducts())
                .isEqualTo(productRepository.getAllProducts());
    }

    @Test
    public void testCannotGetModifiedProducts() {
        addNewProductToWarehouse("27", "Sweater", Category.CLOTHES, 7);

        assertThat(productService.getModifiedProducts()).isEmpty();
    }

    @Test
    public void testGettingCategoriesWithProducts() {
        Product raisins =
                returnNewProduct("53", "Raisins", Category.PROVISIONS, 7);
        Product pants =
                returnNewProduct("23", "Pants", Category.CLOTHES, 8);
        Product sweater =
                returnNewProduct("27", "Sweater", Category.CLOTHES, 7);

        productRepository.addProduct(raisins);
        productRepository.addProduct(pants);
        productRepository.addProduct(sweater);

        assertThat(productService.getCategoriesWithProducts())
                .hasSize(2)
                .containsEntry(Category.CLOTHES, pants)
                .containsEntry(Category.PROVISIONS, raisins);
    }

    private void addNewProductToWarehouse(String id, String name,
                                          Category category, int rating) {
        productRepository.addProduct(
                new Product.Builder()
                        .id(id)
                        .name(name)
                        .category(category)
                        .rating(rating)
                        .build()
        );
    }

    private Product returnNewProduct(String id, String name,
                                     Category category, int rating) {
        return new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .build();
    }
}
