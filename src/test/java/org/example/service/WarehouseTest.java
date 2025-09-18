package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class WarehouseTest {
    private final Warehouse warehouse = new Warehouse();

    @Test
    public void testAddingProducts() {
        addNewProductToWarehouse("23", "Pants", Category.CLOTHES, 5);

        assertEquals(1, warehouse.getAllProducts().size());
    }
    @Test
    public void testAddingProductsWithoutName() {
        // A product's name attribute must not be empty.
        assertThatThrownBy(() -> addNewProductToWarehouse("34", "",
                Category.PROVISIONS, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGettingAllProducts() {
        addNewProductToWarehouse("23", "Pants", Category.CLOTHES, 9);
        addNewProductToWarehouse("24", "Shirt", Category.CLOTHES, 8);
        addNewProductToWarehouse("25", "Hat", Category.CLOTHES, 3);

        assertEquals(3, warehouse.getAllProducts().size());
    }

    @Test
    public void testGettingProductById() {
        addNewProductToWarehouse("23", "Pants", Category.CLOTHES, 9);
        addNewProductToWarehouse("24", "Shirt", Category.CLOTHES, 8);

        Product shirt = warehouse.getAllProducts().getLast();

        assertEquals(shirt, warehouse.getProductById("24"));
    }
    @Test
    public void testCannotFindProductById() {
        assertThatThrownBy(() -> warehouse.getProductById("25"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testGettingProductsByCategorySorted() {
        // Sorted by name A-Z.
        Product pants =
                returnNewProduct("23", "Pants", Category.CLOTHES, 9);
        Product shirt =
                returnNewProduct("24", "Shirt", Category.CLOTHES, 8);
        Product hat =
                returnNewProduct("25", "Hat", Category.CLOTHES, 3);

        warehouse.addProduct(pants); // index 0
        warehouse.addProduct(shirt); // index 1
        warehouse.addProduct(hat); // index 2

        List<Product> sortedList = new ArrayList<>();
        sortedList.add(0, hat);
        sortedList.add(1, pants);
        sortedList.add(2, shirt);

        assertEquals(sortedList,
                warehouse.getProductsByCategorySorted(Category.CLOTHES));
    }
    @Test
    public void testCannotFindProductsByCategory() {
        Product raisins =
                returnNewProduct("53", "Raisins", Category.PROVISIONS, 7);
        Product banana =
                returnNewProduct("54", "Banana", Category.PROVISIONS, 8);

        warehouse.addProduct(raisins); // index 0
        warehouse.addProduct(banana); // index 1

        List<Product> sortedList = new ArrayList<>();
        sortedList.add(0, banana);
        sortedList.add(1, raisins);

        // We are trying to sort clothes, but only have provisions in our products.
        assertNotEquals(sortedList,
                warehouse.getProductsByCategorySorted(Category.CLOTHES));
    }

    @Test
    public void testGetProductsCreatedAfterSpecifiedDate() {
        addNewProductToWarehouse("53", "Raisins", Category.PROVISIONS, 7);

        LocalDateTime beforeProduct = LocalDateTime.of(2025,
                9, 10, 11, 59);

        assertThat(warehouse.getProductsCreatedAfter(beforeProduct))
                .isEqualTo(warehouse.getAllProducts());
    }
    @Test
    public void testNoProductsCreatedAfterSpecifiedDate() {
        addNewProductToWarehouse("53", "Raisins", Category.PROVISIONS, 7);

        LocalDateTime afterProduct = LocalDateTime.of(2030,
                9, 10, 12, 1);

        assertThat(warehouse.getProductsCreatedAfter(afterProduct)).isEmpty();
    }

    @Test
    public void testUpdatingAProduct() {
        addNewProductToWarehouse("53", "Raisins", Category.PROVISIONS, 7);

        assertThat(warehouse.updateProduct("53", "Banana",
                Category.PROVISIONS, 8)).isEqualTo(warehouse.getAllProducts());
    }
    @Test
    public void testCannotUpdateAProduct() {
        assertThatThrownBy(() -> warehouse.updateProduct("54", "Banana",
                Category.PROVISIONS, 8))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testGettingModifiedProducts() {
        addNewProductToWarehouse("53", "Raisins",
                Category.PROVISIONS, 7);
        warehouse.updateProduct("53", "Banana",
                Category.PROVISIONS, 8);

        assertThat(warehouse.getModifiedProducts())
                .isEqualTo(warehouse.getAllProducts());
    }
    @Test
    public void testCannotGetModifiedProducts() {
        addNewProductToWarehouse("27", "Sweater", Category.CLOTHES, 7);

        assertThat(warehouse.getModifiedProducts()).isEmpty();
    }

    @Test
    public void testGettingCategoriesWithProducts() {
        Product raisins =
                returnNewProduct("53", "Raisins", Category.PROVISIONS, 7);
        Product pants =
                returnNewProduct("23", "Pants", Category.CLOTHES, 8);
        Product sweater =
                returnNewProduct("27", "Sweater", Category.CLOTHES, 7);

        warehouse.addProduct(raisins);
        warehouse.addProduct(pants);
        warehouse.addProduct(sweater);

        assertThat(warehouse.getCategoriesWithProducts())
                .hasSize(2)
                .containsEntry(Category.CLOTHES, pants)
                .containsEntry(Category.PROVISIONS, raisins);
    }

    private void addNewProductToWarehouse(String id, String name,
                                          Category category, int rating) {
        warehouse.addProduct(
                new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(9)
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
