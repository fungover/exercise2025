package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        assertEquals("Car", warehouse.getAllProducts().getFirst().name());
    }

    @Test
    void addProductFailureEmptyName() {
        Product product = new Product("1", "", Category.TOYS, 5, LocalDate.now(), LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(product));
    }

    @Test
    void addProductFailure_nullProduct() {
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

    @Test
    void updateProductFailureNonExistentProduct() {
        assertThrows(IllegalArgumentException.class, () ->
                warehouse.updateProduct("99", "Failure", Category.BOOKS, 5)
        );
    }

    @Test
    void getAllProductsSuccess() {
        Product p1 = new Product("1", "Car", Category.TOYS, 8, LocalDate.now(), LocalDate.now());
        Product p2 = new Product("2", "Book", Category.BOOKS, 7, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(p1);
        warehouse.addProduct(p2);

        List<Product> products = warehouse.getAllProducts();
        assertEquals(2, products.size());
        assertEquals("Car", products.get(0).name());
        assertEquals("Book", products.get(1).name());
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
    void getProductsCreatedAfterSuccess() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Product p1 = new Product("1", "Computer", Category.ELECTRONICS, 3, yesterday, yesterday);
        Product p2 = new Product("2", "Ticket to Ride",  Category.BOARDGAMES, 4, today, today);
        Product p3 = new Product("3", "Fight Club",  Category.MOVIES, 5, today, today);

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);

        List<Product> results = warehouse.getProductsCreatedAfter(yesterday);

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(p -> p.name().equals("Ticket to Ride")));
        assertTrue(results.stream().anyMatch(p -> p.name().equals("Fight Club")));
    }

    @Test
    void getProductsCreatedAfterFailure() {
        LocalDate nextWeek = LocalDate.now().plusWeeks(1);
        assertTrue(warehouse.getProductsCreatedAfter(nextWeek).isEmpty());
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
        assertEquals("Modified Product", modifiedProducts.getFirst().name());
    }

    @Test
    void getCategoriesWithProducts() {
        Product p1 = new Product("1", "Speakers", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now());
        Product p2 = new Product("2", "Lord of the Flies", Category.BOOKS, 7, LocalDate.now(), LocalDate.now());
        Product p3 = new Product("3", "Rubik's Cube", Category.TOYS, 8, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);

        List<Category> categories = warehouse.getCategoriesWithProducts();

        assertEquals(3, categories.size());
        assertTrue(categories.contains(Category.ELECTRONICS));
        assertTrue(categories.contains(Category.BOOKS));
        assertTrue(categories.contains(Category.TOYS));
    }

    @Test
    void countProductsInCategory() {
        warehouse.addProduct(new Product("1", "iPad", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now()));
        warehouse.addProduct(new Product("2", "TV", Category.ELECTRONICS, 8, LocalDate.now(), LocalDate.now()));
        warehouse.addProduct(new Product("3", "Titanic", Category.MOVIES, 7, LocalDate.now(), LocalDate.now()));

        assertEquals(2, warehouse.countProductsInCategory(Category.ELECTRONICS));
        assertEquals(1, warehouse.countProductsInCategory(Category.MOVIES));
        assertEquals(0, warehouse.countProductsInCategory(Category.BOOKS));
    }

    @Test
    void getProductInitialsMap() {
        warehouse.addProduct(new Product("1", "Laptop", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now()));
        warehouse.addProduct(new Product("2", "iPhone", Category.ELECTRONICS, 8, LocalDate.now(), LocalDate.now()));
        warehouse.addProduct(new Product("3", "Harry Potter", Category.BOOKS, 7, LocalDate.now(), LocalDate.now()));
        warehouse.addProduct(new Product("4", "Headphones", Category.ELECTRONICS, 6, LocalDate.now(), LocalDate.now()));

        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();

        assertEquals(3, initialsMap.size());
        assertEquals(1, initialsMap.get('L'));
        assertEquals(1, initialsMap.get('I'));
        assertEquals(2, initialsMap.get('H'));
    }

    @Test
    void getTopRatedProductsThisMonth() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);

        warehouse.addProduct(new Product("1", "Laptop", Category.ELECTRONICS, 10, today, today));
        warehouse.addProduct(new Product("2", "iPhone", Category.ELECTRONICS, 9, today, today));
        warehouse.addProduct(new Product("3", "Harry Potter", Category.BOOKS, 10, today, today));
        warehouse.addProduct(new Product("4", "Old Product", Category.BOOKS, 10, lastMonth, lastMonth));

        List<Product> topProducts = warehouse.getTopRatedProductsThisMonth();

        assertEquals(2, topProducts.size());
        assertEquals("Harry Potter", topProducts.get(0).name());
        assertEquals("Laptop", topProducts.get(1).name());
        assertTrue(topProducts.stream().noneMatch(p -> p.name().equals("Old Product")));
    }
}