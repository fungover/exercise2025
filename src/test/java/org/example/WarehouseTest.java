package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void getProductsBasedOnDateSuccess() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Product p1 = new Product("1", "Computer", Category.ELECTRONICS, 3, yesterday, yesterday);
        Product p2 = new Product("2", "Ticket to Ride",  Category.BOARDGAMES, 4, today, today);
        Product p3 = new Product("3", "Fight Club",  Category.MOVIES, 5, today, today);

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);

        List<Product> results = warehouse.getProductsBasedOnDateSuccess(yesterday);

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(p -> p.name().equals("Ticket to Ride")));
        assertTrue(results.stream().anyMatch(p -> p.name().equals("Fight Club")));
    }

    @Test
    void getProductsBasedOnDateFailure() {
        LocalDate nextWeek = LocalDate.now().plusWeeks(1);

        assertTrue(warehouse.getProductsBasedOnDateSuccess(nextWeek).isEmpty());
    }

    @Test
    void getModifiedProductsSuccess() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Product p1 = new Product("1", "Unmodified Product", Category.TOYS, 8, today, today);
        Product p2 = new Product("2", "Modified Product", Category.BOOKS, 3, yesterday, today);

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);

        List<Product> modifiedProducts = warehouse.getModifiedProducts();

        assertEquals(1, modifiedProducts.size());
        assertEquals("Modified Product", modifiedProducts.get(0).name());
    }
}