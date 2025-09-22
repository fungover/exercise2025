package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    void addProduct() {

        Product product = new Product.ProductBuilder()
                .id("1")
                .name("Harry Potter and the Deathly Hallows")
                .category(Category.BOOKS)
                .rating(9)
                .build();

        warehouse.addProduct(product);

        assertEquals(1, warehouse.getAllProducts().size());
        assertTrue(warehouse.getAllProducts().contains(product));
    }

    @Test
    void addProductEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product.ProductBuilder()
                    .id("1")
                    .name("")
                    .category(Category.BOOKS)
                    .rating(9)
                    .build(); // the validation is here and testing that
        });
    }

    @Test
    void updateProduct() {

        Product product = new Product.ProductBuilder()
                .id("1")
                .name("Harry Potter and the deathly hollows")
                .category(Category.BOOKS)
                .rating(9)
                .build();

        warehouse.addProduct(product);

        warehouse.updateProduct("1", "HOKA One One", Category.SHOES, 10);

        Product updatedProduct = warehouse.getProductById("1");

        assertEquals("HOKA One One", updatedProduct.name());
        assertEquals(Category.SHOES, updatedProduct.category());
        assertEquals(10, updatedProduct.rating());
    }

    @Test
    void updateProductIdThatDontExistThrowsException() {

        assertThrows(IllegalArgumentException.class, () ->
                warehouse.updateProduct("99", "eleventyelevenonetyone", Category.BOOKS, 5)
        );
    }

    @Test
    void getAllProductsReturnsAllProducts() {
        LocalDateTime now = LocalDateTime.now();

        Product product1 = new Product("1", "Pizza", Category.FOOD, 100, now, now);
        Product product2 = new Product("2", "Tacos", Category.FOOD, 99, now, now);

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        List<Product> allProducts = warehouse.getAllProducts();

        assertEquals(2, allProducts.size());
        assertTrue(allProducts.contains(product1));
        assertTrue(allProducts.contains(product2));
    }

    @Test
    void getAllProductsEmptyWarehouseReturnsEmptyList() {
        List<Product> allProducts = warehouse.getAllProducts();

        assertTrue(allProducts.isEmpty(), "Expected empty list when no products are added");
    }

    @Test
    void getProductByIdReturnsCorrectProduct() {
        LocalDateTime now = LocalDateTime.now();

        Product product = new Product("1", "Peppa Pigs adventure", Category.BOOKS, 7, now, now);
        warehouse.addProduct(product);

        Product result = warehouse.getProductById("1");

        assertEquals(product, result);
    }

    @Test
    void getProductByIdNonExistingIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            warehouse.getProductById("99");
        });
    }

    @Test
    void getProductsByCategorySortedReturnsSortedProducts() {
        LocalDateTime now = LocalDateTime.now();

        Product p1 = new Product("1", "Banana", Category.FOOD, 5, now, now);
        Product p2 = new Product("2", "Apple", Category.FOOD, 7, now, now);
        Product p3 = new Product("3", "Carrot", Category.FOOD, 6, now, now);
        Product p4 = new Product("4", "Bridget Jones diary", Category.BOOKS, 9, now, now);

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);
        warehouse.addProduct(p4);

        List<Product> sortedFood = warehouse.getProductsByCategorySorted(Category.FOOD);

        assertEquals(3, sortedFood.size());
        assertEquals("Apple", sortedFood.get(0).name());
        assertEquals("Banana", sortedFood.get(1).name());
        assertEquals("Carrot", sortedFood.get(2).name());
    }

    @Test
    void getProductsByCategorySortedEmptyCategoryReturnsEmptyList() {
        LocalDateTime now = LocalDateTime.now();

        warehouse.addProduct(new Product("1", "Apple", Category.FOOD, 5, now, now));
        warehouse.addProduct(new Product("2", "Book", Category.BOOKS, 8, now, now));

        List<Product> result = warehouse.getProductsByCategorySorted(Category.CLOTHES);

        assertTrue(result.isEmpty(), "Expected empty list for category with no products");
    }

    @Test
    void getProductsCreatedAfterReturnsCorrectProducts() {
        LocalDateTime now = LocalDateTime.now();

        Product oldProduct = new Product("1", "Old Book", Category.BOOKS, 5,
                now.minusDays(10), now.minusDays(10));
        Product recentProduct = new Product("2", "New Book", Category.BOOKS, 9,
                now.minusDays(1), now.minusDays(1));

        warehouse.addProduct(oldProduct);
        warehouse.addProduct(recentProduct);

        List<Product> result = warehouse.getProductsCreatedAfter(LocalDate.now().minusDays(5));

        assertEquals(1, result.size());
        assertEquals("New Book", result.get(0).name());
    }

    @Test
    void getProductsCreatedAfterNoProductsAfterDateReturnsEmptyList() {
        LocalDateTime now = LocalDateTime.now();

        warehouse.addProduct(new Product("1", "Old Book", Category.BOOKS, 5, now.minusDays(10), now.minusDays(10)));
        warehouse.addProduct(new Product("2", "Older Food", Category.FOOD, 7, now.minusDays(5), now.minusDays(5)));

        List<Product> result = warehouse.getProductsCreatedAfter(LocalDate.now());

        assertTrue(result.isEmpty(), "Expected empty list when no products are created after the given date");
    }


    @Test
    void getModifiedProductsReturnsOnlyModified() {
        LocalDateTime createdTime = LocalDateTime.now().minusMinutes(1);

        Product product1 = new Product("1", "Original Book", Category.BOOKS, 7, createdTime, createdTime);
        warehouse.addProduct(product1);

        warehouse.updateProduct("1", "Updated Book", Category.BOOKS, 8);

        Product product2 = new Product("2", "Fresh Food", Category.FOOD, 9, LocalDateTime.now(), LocalDateTime.now());
        warehouse.addProduct(product2);

        List<Product> modified = warehouse.getModifiedProducts();

        assertEquals(1, modified.size());
        assertEquals("Updated Book", modified.get(0).name());
    }

}