package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    ProductService productService;

    @BeforeEach
    void setUp() {
        ProductRepository repository = new InMemoryProductRepository();
        productService = new ProductService(repository);
    }

    @Test
    void addProduct() {
        Product product = new Product.Builder()
                .id("1")
                .name("Harry Potter and the Deathly Hallows")
                .category(Category.BOOKS)
                .rating(9)
                .build();

        productService.addProduct(product);

        assertEquals(1, productService.getAllProducts().size());
        assertTrue(productService.getAllProducts().contains(product));
    }

    @Test
    void addProductEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product.Builder()
                    .id("1")
                    .name("")
                    .category(Category.BOOKS)
                    .rating(9)
                    .build();
        });
    }

    @Test
    void updateProduct() {

        Product product = new Product.Builder()
                .id("1")
                .name("Harry Potter and the deathly hollows")
                .category(Category.BOOKS)
                .rating(9)
                .build();

        productService.addProduct(product);

        productService.updateProduct("1", "HOKA One One", Category.SHOES, 10);

        Product updatedProduct = productService.getProductById("1");

        assertEquals("HOKA One One", updatedProduct.name());
        assertEquals(Category.SHOES, updatedProduct.category());
        assertEquals(10, updatedProduct.rating());
    }

    @Test
    void updateProductIdThatDontExistThrowsException() {

        assertThrows(IllegalArgumentException.class, () ->
                productService.updateProduct("99", "eleventyelevenonetyone", Category.BOOKS, 5)
        );
    }

    @Test
    void getAllProductsReturnsAllProducts() {
        LocalDateTime now = LocalDateTime.now();

        Product product1 = new Product.Builder()
                .id("1")
                .name("Pizza")
                .category(Category.FOOD)
                .rating(10)
                .build();

        Product product2 = new Product.Builder()
                .id("2")
                .name("Tacos")
                .category(Category.FOOD)
                .rating(9)
                .build();

        productService.addProduct(product1);
        productService.addProduct(product2);

        List<Product> allProducts = productService.getAllProducts();

        assertEquals(2, allProducts.size());
        assertTrue(allProducts.contains(product1));
        assertTrue(allProducts.contains(product2));
    }

    @Test
    void getAllProductsEmptyWarehouseReturnsEmptyList() {
        List<Product> allProducts = productService.getAllProducts();

        assertTrue(allProducts.isEmpty(), "Expected empty list when no products are added");
    }

    @Test
    void getProductByIdReturnsCorrectProduct() {

        Product product = new Product.Builder()
                .id("1")
                .name("Peppa Pigs adventure")
                .category(Category.BOOKS)
                .rating(7)
                .build();
        productService.addProduct(product);

        Product result = productService.getProductById("1");

        assertEquals(product, result);
    }

    @Test
    void getProductByIdNonExistingIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductById("99");
        });
    }

    @Test
    void getProductsByCategorySortedReturnsSortedProducts() {

        Product p1 = new Product.Builder()
                .id("1")
                .name("Banana")
                .category(Category.FOOD)
                .rating(8)
                .build();
        Product p2 = new Product.Builder()
                .id("2")
                .name("Apple")
                .category(Category.FOOD)
                .rating(8)
                .build();
        Product p3 = new Product.Builder()
                .id("3")
                .name("Carrot")
                .category(Category.FOOD)
                .rating(5)
                .build();
        Product p4 = new Product.Builder()
                .id("4")
                .name("Bridget Jones diary")
                .category(Category.BOOKS)
                .rating(9)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);
        productService.addProduct(p3);
        productService.addProduct(p4);

        List<Product> sortedFood = productService.getProductsByCategorySorted(Category.FOOD);

        assertEquals(3, sortedFood.size());
        assertEquals("Apple", sortedFood.get(0).name());
        assertEquals("Banana", sortedFood.get(1).name());
        assertEquals("Carrot", sortedFood.get(2).name());
    }

    @Test
    void getProductsByCategorySortedEmptyCategoryReturnsEmptyList() {

        Product p1 = new Product.Builder()
                .id("1")
                .name("Apple")
                .category(Category.FOOD)
                .rating(5)
                .build();

        Product p2 = new Product.Builder()
                .id("2")
                .name("Book")
                .category(Category.BOOKS)
                .rating(8)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);

        List<Product> result = productService.getProductsByCategorySorted(Category.CLOTHES);

        assertTrue(result.isEmpty(), "Expected empty list for category with no products");
    }

    @Test
    void getProductsCreatedAfterReturnsCorrectProducts() {
        LocalDateTime now = LocalDateTime.now();

        Product oldProduct = new Product.Builder()
                .id("1")
                .name("Old Book")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(now.minusDays(10))
                .modifiedDate(now.minusDays(10))
                .build();
        Product recentProduct = new Product.Builder()
                .id("2")
                .name("New Book")
                .category(Category.BOOKS)
                .rating(9)
                .createdDate(now.minusDays(1))
                .modifiedDate(now.minusDays(1))
                .build();

        productService.addProduct(oldProduct);
        productService.addProduct(recentProduct);

        List<Product> result = productService.getProductsCreatedAfter(LocalDate.now().minusDays(5));

        assertEquals(1, result.size());
        assertEquals("New Book", result.get(0).name());
    }

    @Test
    void getProductsCreatedAfterNoProductsAfterDateReturnsEmptyList() {
        LocalDateTime now = LocalDateTime.now();

        productService.addProduct(new Product.Builder()
                .id("1")
                .name("Old Book")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(now.minusDays(10))
                .modifiedDate(now.minusDays(10))
                .build());
        productService.addProduct(new Product.Builder()
                .id("2")
                .name("Older Food")
                .category(Category.FOOD)
                .rating(7)
                .createdDate(now.minusDays(5))
                .modifiedDate(now.minusDays(5))
                .build());

        List<Product> result = productService.getProductsCreatedAfter(LocalDate.now());

        assertTrue(result.isEmpty(), "Expected empty list when no products are created after the given date");
    }


    @Test
    void getModifiedProductsReturnsOnlyModified() {
        LocalDateTime createdTime = LocalDateTime.now().minusMinutes(1);

        Product product1 = new Product.Builder()
                .id("1")
                .name("Original Book")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(createdTime)
                .modifiedDate(createdTime)
                .build();
        productService.addProduct(product1);

        productService.updateProduct("1", "Updated Book", Category.BOOKS, 8);

        Product product2 = new Product.Builder()
                .id("2")
                .name("Fresh Food")
                .category(Category.FOOD)
                .rating(9)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        productService.addProduct(product2);

        List<Product> modified = productService.getModifiedProducts();

        assertEquals(1, modified.size());
        assertEquals("Updated Book", modified.get(0).name());
    }

}