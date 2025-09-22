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

    ProductService warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new ProductService();
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

        Product product1 = new Product.ProductBuilder()
                .id("1")
                .name("Pizza")
                .category(Category.FOOD)
                .rating(10)
                .build();

        Product product2 = new Product.ProductBuilder()
                .id("2")
                .name("Tacos")
                .category(Category.FOOD)
                .rating(9)
                .build();

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

        Product product = new Product.ProductBuilder()
                .id("1")
                .name("Peppa Pigs adventure")
                .category(Category.BOOKS)
                .rating(7)
                .build();
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

        Product p1 = new Product.ProductBuilder()
                .id("1")
                .name("Banana")
                .category(Category.FOOD)
                .rating(8)
                .build();
        Product p2 = new Product.ProductBuilder()
                .id("2")
                .name("Apple")
                .category(Category.FOOD)
                .rating(8)
                .build();
        Product p3 = new Product.ProductBuilder()
                .id("3")
                .name("Carrot")
                .category(Category.FOOD)
                .rating(5)
                .build();
        Product p4 = new Product.ProductBuilder()
                .id("4")
                .name("Bridget Jones diary")
                .category(Category.BOOKS)
                .rating(9)
                .build();

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

        Product p1 = new Product.ProductBuilder()
                .id("1")
                .name("Apple")
                .category(Category.FOOD)
                .rating(5)
                .build();

        Product p2 = new Product.ProductBuilder()
                .id("2")
                .name("Book")
                .category(Category.BOOKS)
                .rating(8)
                .build();

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);

        List<Product> result = warehouse.getProductsByCategorySorted(Category.CLOTHES);

        assertTrue(result.isEmpty(), "Expected empty list for category with no products");
    }

    @Test
    void getProductsCreatedAfterReturnsCorrectProducts() {
        LocalDateTime now = LocalDateTime.now();

        Product oldProduct = new Product.ProductBuilder()
                .id("1")
                .name("Old Book")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(now.minusDays(10))
                .modifiedDate(now.minusDays(10))
                .build();
        Product recentProduct = new Product.ProductBuilder()
                .id("2")
                .name("New Book")
                .category(Category.BOOKS)
                .rating(9)
                .createdDate(now.minusDays(1))
                .modifiedDate(now.minusDays(1))
                .build();

        warehouse.addProduct(oldProduct);
        warehouse.addProduct(recentProduct);

        List<Product> result = warehouse.getProductsCreatedAfter(LocalDate.now().minusDays(5));

        assertEquals(1, result.size());
        assertEquals("New Book", result.get(0).name());
    }

    @Test
    void getProductsCreatedAfterNoProductsAfterDateReturnsEmptyList() {
        LocalDateTime now = LocalDateTime.now();

        warehouse.addProduct(new Product.ProductBuilder()
                .id("1")
                .name("Old Book")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(now.minusDays(10))
                .modifiedDate(now.minusDays(10))
                .build());
        warehouse.addProduct(new Product.ProductBuilder()
                .id("2")
                .name("Older Food")
                .category(Category.FOOD)
                .rating(7)
                .createdDate(now.minusDays(5))
                .modifiedDate(now.minusDays(5))
                .build());

        List<Product> result = warehouse.getProductsCreatedAfter(LocalDate.now());

        assertTrue(result.isEmpty(), "Expected empty list when no products are created after the given date");
    }


    @Test
    void getModifiedProductsReturnsOnlyModified() {
        LocalDateTime createdTime = LocalDateTime.now().minusMinutes(1);

        Product product1 = new Product.ProductBuilder()
                .id("1")
                .name("Original Book")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(createdTime)
                .modifiedDate(createdTime)
                .build();
        warehouse.addProduct(product1);

        warehouse.updateProduct("1", "Updated Book", Category.BOOKS, 8);

        Product product2 = new Product.ProductBuilder()
                .id("2")
                .name("Fresh Food")
                .category(Category.FOOD)
                .rating(9)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        warehouse.addProduct(product2);

        List<Product> modified = warehouse.getModifiedProducts();

        assertEquals(1, modified.size());
        assertEquals("Updated Book", modified.get(0).name());
    }

}