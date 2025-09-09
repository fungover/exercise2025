package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WarehouseTest {
    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    void addProductSuccess() {
        Product product = new Product("1", "Car", Category.TOYS, 8, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(product);

        assertEquals(1, warehouse.getAllProducts().size());
        assertEquals("Car", warehouse.getAllProducts().get(0).name());
    }

    @Test
    void addProductFailure() {
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(null));
    }

    @Test
    void updateProductSuccess() {
        Product product = new Product("1", "Car", Category.TOYS, 8, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(product);

        Product updated = warehouse.updateProduct(
                "1",
                "Laptop",
                Category.ELECTRONICS,
                10
        );
        assertEquals("Laptop", updated.name());
        assertEquals(10, updated.rating());
    }

    @ Test
    void updateProductFailure() {
        assertThrows(IllegalArgumentException.class, () ->
                warehouse.updateProduct("99", "Failure", Category.BOOKS, 5)
        );
    }

    @Test
    void getAllProductsSuccess() {
        List<Product> products = warehouse.getAllProducts();
    }

    @Test
    void getProductByIdSuccess() {
        Product product = new Product("2", "Harry Potter", Category.BOOKS, 8, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(product);

        Product result = warehouse.getProductById("2");


        assertEquals("2", result.id());
        assertEquals("Harry Potter", result.name());
    }

    @Test
    void getProductByIdFailure() {
        assertThrows(IllegalArgumentException.class, () -> warehouse.getProductById("404"));
    }

    @Test
    void getProductsByCategorySuccess() {
        Product p1 = new Product("1", "Laptop", Category.ELECTRONICS, 8, LocalDate.now(), LocalDate.now());
        Product p2 = new Product("2", "iPhone", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now());
        Product p3 = new Product("3", "Harry Potter", Category.BOOKS, 7, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);

        List<Product> electronics = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);

        assertEquals(2, electronics.size());
        assertEquals("iPhone", electronics.get(0).name());
        assertEquals("Laptop", electronics.get(1).name());
    }
}
