package org.example;

import org.example.Repository.InMemoryProductRepository;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(new InMemoryProductRepository());
    }

    @Test
    void addProductSuccess() {
        Product product = new Product.Builder()
                .id("1")
                .name("Car")
                .category(Category.TOYS)
                .rating(8)
                .price(19.99)
                .build();

        productService.addProduct(product);

        assertEquals(1, productService.getAllProducts().size());
        assertEquals("Car", productService.getAllProducts().get(0).getName());
    }

    @Test
    void addProductFailureEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product.Builder()
                        .id("1")
                        .name("")
                        .category(Category.TOYS)
                        .rating(5)
                        .price(9.99)
                        .build()
        );
    }

    @Test
    void addProductFailure_nullProduct() {
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(null));
    }

    @Test
    void updateProductSuccess() {
        Product product = new Product.Builder()
                .id("1")
                .name("Car")
                .category(Category.TOYS)
                .rating(8)
                .price(19.99)
                .build();
        productService.addProduct(product);

        Product updated = productService.updateProduct(
                "1",
                "Laptop",
                Category.ELECTRONICS,
                10
        );
        assertEquals("Laptop", updated.getName());
        assertEquals(10, updated.getRating());
    }

    @Test
    void updateProductFailureNonExistentProduct() {
        assertThrows(IllegalArgumentException.class, () ->
                productService.updateProduct("99", "Failure", Category.BOOKS, 5)
        );
    }

    @Test
    void getAllProductsSuccess() {
        Product p1 = new Product.Builder()
                .id("1")
                .name("Car")
                .category(Category.TOYS)
                .rating(8)
                .price(19.99)
                .build();
        Product p2 = new Product.Builder()
                .id("2")
                .name("Book")
                .category(Category.BOOKS)
                .rating(7)
                .price(9.99)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);

        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());
        assertEquals("Car", products.get(0).getName());
        assertEquals("Book", products.get(1).getName());
    }

    @Test
    void getProductByIdSuccess() {
        Product product = new Product.Builder()
                .id("2")
                .name("Harry Potter")
                .category(Category.BOOKS)
                .rating(8)
                .price(19.99)
                .build();

        productService.addProduct(product);

        Product result = productService.getProductById("2");

        assertEquals("2", result.getId());
        assertEquals("Harry Potter", result.getName());
    }

    @Test
    void getProductByIdFailure() {
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById("404"));
    }

    @Test
    void getProductsByCategorySuccess() {
        Product p1 = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(999.99)
                .build();

        Product p2 = new Product.Builder()
                .id("2")
                .name("iPhone")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(1199.99)
                .build();

        Product p3 = new Product.Builder()
                .id("3")
                .name("Harry Potter")
                .category(Category.BOOKS)
                .rating(7)
                .price(19.99)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);
        productService.addProduct(p3);

        List<Product> electronics = productService.getProductsByCategorySorted(Category.ELECTRONICS);

        assertEquals(2, electronics.size());
        assertEquals("iPhone", electronics.get(0).getName());
        assertEquals("Laptop", electronics.get(1).getName());
    }

    @Test
    void getProductsCreatedAfterSuccess() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Product p1 = new Product.Builder()
                .id("1")
                .name("Computer")
                .category(Category.ELECTRONICS)
                .rating(3)
                .price(499.99)
                .createdDate(yesterday)
                .modifiedDate(yesterday)
                .build();

        Product p2 = new Product.Builder()
                .id("2")
                .name("Ticket to Ride")
                .category(Category.BOARDGAMES)
                .rating(4)
                .price(29.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product p3 = new Product.Builder()
                .id("3")
                .name("Fight Club")
                .category(Category.MOVIES)
                .rating(5)
                .price(14.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);
        productService.addProduct(p3);

        List<Product> results = productService.getProductsCreatedAfter(yesterday);

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(p -> p.getName().equals("Ticket to Ride")));
        assertTrue(results.stream().anyMatch(p -> p.getName().equals("Fight Club")));
    }

    @Test
    void getProductsCreatedAfterFailure() {
        LocalDate nextWeek = LocalDate.now().plusWeeks(1);
        assertTrue(productService.getProductsCreatedAfter(nextWeek).isEmpty());
    }

    @Test
    void getModifiedProductsSuccess() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Product unmodified = new Product.Builder()
                .id("1")
                .name("Unmodified Product")
                .category(Category.TOYS)
                .rating(8)
                .price(19.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product modified = new Product.Builder()
                .id("2")
                .name("Modified Product")
                .category(Category.BOOKS)
                .rating(3)
                .price(12.99)
                .createdDate(yesterday)
                .modifiedDate(today)
                .build();

        productService.addProduct(unmodified);
        productService.addProduct(modified);

        List<Product> modifiedProducts = productService.getModifiedProducts();

        assertEquals(1, modifiedProducts.size());
        assertEquals("Modified Product", modifiedProducts.getFirst().getName());
    }

    @Test
    void getCategoriesWithProducts() {
        LocalDate today = LocalDate.now();

        Product speakers = new Product.Builder()
                .id("1")
                .name("Speakers")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(199.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product book = new Product.Builder()
                .id("2")
                .name("Lord of the Flies")
                .category(Category.BOOKS)
                .rating(7)
                .price(12.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product rubiksCube = new Product.Builder()
                .id("3")
                .name("Rubik's Cube")
                .category(Category.TOYS)
                .rating(8)
                .price(14.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        productService.addProduct(speakers);
        productService.addProduct(book);
        productService.addProduct(rubiksCube);

        List<Category> categories = productService.getCategoriesWithProducts();

        assertEquals(3, categories.size());
        assertTrue(categories.contains(Category.ELECTRONICS));
        assertTrue(categories.contains(Category.BOOKS));
        assertTrue(categories.contains(Category.TOYS));
    }

    @Test
    void countProductsInCategory() {
        LocalDate today = LocalDate.now();

        Product iPad = new Product.Builder()
                .id("1")
                .name("iPad")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(499.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product tv = new Product.Builder()
                .id("2")
                .name("TV")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(799.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product titanic = new Product.Builder()
                .id("3")
                .name("Titanic")
                .category(Category.MOVIES)
                .rating(7)
                .price(14.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        productService.addProduct(iPad);
        productService.addProduct(tv);
        productService.addProduct(titanic);

        assertEquals(2, productService.countProductsInCategory(Category.ELECTRONICS));
        assertEquals(1, productService.countProductsInCategory(Category.MOVIES));
        assertEquals(0, productService.countProductsInCategory(Category.BOOKS));
    }

    @Test
    void getProductInitialsMap() {
        LocalDate today = LocalDate.now();

        Product laptop = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(599.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product iphone = new Product.Builder()
                .id("2")
                .name("iPhone")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(999.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product harryPotter = new Product.Builder()
                .id("3")
                .name("Harry Potter")
                .category(Category.BOOKS)
                .rating(7)
                .price(19.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product headphones = new Product.Builder()
                .id("4")
                .name("Headphones")
                .category(Category.ELECTRONICS)
                .rating(6)
                .price(49.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        productService.addProduct(laptop);
        productService.addProduct(iphone);
        productService.addProduct(harryPotter);
        productService.addProduct(headphones);


        Map<Character, Integer> initialsMap = productService.getProductInitialsMap();

        assertEquals(3, initialsMap.size());
        assertEquals(1, initialsMap.get('L'));
        assertEquals(1, initialsMap.get('I'));
        assertEquals(2, initialsMap.get('H'));
    }

    @Test
    void getTopRatedProductsThisMonth() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);

        Product laptop = new Product.Builder()
                .name("Laptop")
                .id("1")
                .category(Category.ELECTRONICS)
                .rating(10)
                .price(599.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product iphone = new Product.Builder()
                .name("iPhone")
                .id("2")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(999.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product harryPotter = new Product.Builder()
                .id("3")
                .name("Harry Potter")
                .category(Category.BOOKS)
                .rating(10)
                .price(19.99)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product oldProduct = new Product.Builder()
                .id("4")
                .name("Old Product")
                .category(Category.BOOKS)
                .rating(10)
                .price(9.99)
                .createdDate(lastMonth)
                .modifiedDate(lastMonth)
                .build();


        productService.addProduct(laptop);
        productService.addProduct(iphone);
        productService.addProduct(harryPotter);
        productService.addProduct(oldProduct);

        List<Product> topProducts = productService.getTopRatedProductsThisMonth();

        assertEquals(2, topProducts.size());
        assertEquals("Harry Potter", topProducts.get(0).getName());
        assertEquals("Laptop", topProducts.get(1).getName());
        assertTrue(topProducts.stream().noneMatch(p -> p.getName().equals("Old Product")));
    }
}