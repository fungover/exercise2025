package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;
    private Clock fixed;

    @BeforeEach
    void setup() {
        // Europe/Stockholm, 2025-09-08 12:00 local (10:00Z)
        fixed = Clock.fixed(Instant.parse("2025-09-08T10:00:00Z"), ZoneId.of("Europe/Stockholm"));
        ProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        productService = new ProductService(fixed, inMemoryProductRepository);
    }

    /** getAllProducts method */
    @Test
    void getAllProducts_returnsEmptyWhenNothingAdded() {
        var all = productService.getAllProducts();
        assertTrue(all.isEmpty());
    }

    @Test
    void getAllProducts_listsTwoAfterAddingTwo() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);

        Product testProduct1 = Product.builder()
                .id("id1")
                .name("Product1")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(createdAt1)
                .modifiedDate(createdAt1)
                .build();

        Product testProduct2 = Product.builder()
                .id("id2")
                .name("Product2")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(createdAt2)
                .modifiedDate(createdAt2)
                .build();

        productService.addProduct(testProduct1);
        productService.addProduct(testProduct2);

        var all = productService.getAllProducts();
        assertEquals(2, all.size());
        assertTrue(all.contains(testProduct1));
        assertTrue(all.contains(testProduct2));
    }

    /** addProduct method */
    @Test
    void addProduct_success_addsToService() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product testProduct = Product.builder()
                .id("id1")
                .name("Product1")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(createdAt)
                .modifiedDate(createdAt)
                .build();

        productService.addProduct(testProduct);

        var all = productService.getAllProducts();
        assertEquals(1, all.size());
        assertTrue(all.contains(testProduct));
    }

    @Test
    void addProduct_duplicateId_throwsIllegalArgumentException() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);

        Product testProduct1 = Product.builder()
                .id("id1")
                .name("Product1")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt1)
                .modifiedDate(createdAt1)
                .build();

        Product testProduct2 = Product.builder()
                .id("id1")
                .name("Product2")
                .category(Category.TOYS)
                .rating(8)
                .createdDate(createdAt2)
                .modifiedDate(createdAt2)
                .build();

        productService.addProduct(testProduct1);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> productService.addProduct(testProduct2));
        assertTrue(exception.getMessage().contains("duplicate id"));
    }

    /** getProductById method */
    @Test
    void getProductById_success_getCorrectProductById() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product testProduct = Product.builder()
                .id("id1")
                .name("Product1")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(createdAt)
                .modifiedDate(createdAt)
                .build();

        productService.addProduct(testProduct);

        var testProductById = productService.getProductById(testProduct.id());

        assertEquals(Optional.of(testProduct), testProductById);
    }

    @Test
    void getProductById_nullId_throwsNullPointerException() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> productService.getProductById(null));
        assertTrue(exception.getMessage().contains("id"));
    }

    @Test
    void getProductById_blankId_throwsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> productService.getProductById("  "));
        assertTrue(exception.getMessage().contains("id required"));
    }

    /** updateProduct method */
    @Test
    void updateProduct_success_updatesFields_andPersists() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 9, 0);

        Product original = Product.builder()
                .id("id1")
                .name("Original")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt)
                .modifiedDate(createdAt)
                .build();

        productService.addProduct(original);

        Product updated = productService.updateProduct("id1", "New Name", Category.TOYS, 9, 100);

        assertEquals("id1", updated.id());
        assertEquals("New Name", updated.name());
        assertEquals(Category.TOYS, updated.category());
        assertEquals(9, updated.rating());
        assertEquals(100,updated.price());
        assertEquals(createdAt, updated.createdDate());
        assertEquals(LocalDateTime.of(2025, 9, 8, 12, 0), updated.modifiedDate());
        assertEquals(Optional.of(updated), productService.getProductById("id1"));
    }

    @Test
    void updateProduct_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> productService.updateProduct(null, "test", Category.BOOKS, 7, 100));
    }

    @Test
    void updateProduct_blankId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct("   ", "test", Category.BOOKS, 7, 100));
    }

    @Test
    void updateProduct_missingId_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class,
                () -> productService.updateProduct("missing-id", "test", Category.BOOKS, 7, 100));
    }

    /** getProductsByCategorySorted method */
    @Test
    void getProductsByCategorySorted_success_filtersAndSorts() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);
        LocalDateTime createdAt3 = LocalDateTime.of(2025, 9, 3, 12, 0);

        Product testProduct1 = Product.builder()
                .id("id1")
                .name("C")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt1)
                .modifiedDate(createdAt1)
                .build();

        Product testProduct2 = Product.builder()
                .id("id2")
                .name("B")
                .category(Category.TOYS)
                .rating(8)
                .createdDate(createdAt2)
                .modifiedDate(createdAt2)
                .build();

        Product testProduct3 = Product.builder()
                .id("id3")
                .name("A")
                .category(Category.TOYS)
                .rating(8)
                .createdDate(createdAt3)
                .modifiedDate(createdAt3)
                .build();

        productService.addProduct(testProduct1);
        productService.addProduct(testProduct2);
        productService.addProduct(testProduct3);

        var result = productService.getProductsByCategorySorted(Category.TOYS);

        assertEquals(List.of("A", "B"),
                result.stream().map(Product::name).toList());
    }

    @Test
    void getProductsByCategorySorted_noProductsInCategory_returnsEmptyList() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);

        Product product1 = Product.builder()
                .id("id1")
                .name("A")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt1)
                .modifiedDate(createdAt1)
                .build();

        Product product2 = Product.builder()
                .id("id2")
                .name("B")
                .category(Category.TOYS)
                .rating(8)
                .createdDate(createdAt2)
                .modifiedDate(createdAt2)
                .build();

        productService.addProduct(product1);
        productService.addProduct(product2);

        var result = productService.getProductsByCategorySorted(Category.ELECTRONICS);

        assertTrue(result.isEmpty());
    }

    @Test
    void getProductsByCategorySorted_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> productService.getProductsByCategorySorted(null));
    }

    /** getProductsCreatedAfter method */
    @Test
    void getProductsCreatedAfter_success_filtersStrictlyAfterDate() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);
        LocalDateTime createdAt3 = LocalDateTime.of(2025, 9, 3, 12, 0);

        Product product1 = Product.builder()
                .id("id1")
                .name("sep01")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt1)
                .modifiedDate(createdAt1)
                .build();

        Product product2 = Product.builder()
                .id("id2")
                .name("sep02")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt2)
                .modifiedDate(createdAt2)
                .build();

        Product product3 = Product.builder()
                .id("id3")
                .name("sep03")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt3)
                .modifiedDate(createdAt3)
                .build();

        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);

        var result = productService.getProductsCreatedAfter(LocalDate.of(2025, 9, 2));

        assertEquals(List.of("sep03"), result.stream().map(Product::name).toList());
    }

    @Test
    void getProductsCreatedAfter_noMatches_returnsEmpty() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product product = Product.builder()
                .id("id1")
                .name("sep01")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt)
                .modifiedDate(createdAt)
                .build();

        productService.addProduct(product);

        var result = productService.getProductsCreatedAfter(LocalDate.of(2025, 9, 1));
        assertTrue(result.isEmpty());
    }

    @Test
    void getProductsCreatedAfter_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> productService.getProductsCreatedAfter(null));
    }

    /** getModifiedProducts method */
    @Test
    void getModifiedProducts_success_returnsOnlyUpdatedOnes() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 10, 0);

        Product product1 = Product.builder()
                .id("id1")
                .name("A")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt)
                .modifiedDate(createdAt)
                .build();

        Product product2 = Product.builder()
                .id("id2")
                .name("B")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt)
                .modifiedDate(createdAt)
                .build();

        productService.addProduct(product1);
        productService.addProduct(product2);

        productService.updateProduct("id2", "updated product", Category.BOOKS, 7, 100);

        var result = productService.getModifiedProducts();
        assertEquals(List.of("id2"), result.stream().map(Product::id).toList());
    }

    @Test
    void getModifiedProducts_noneModified_returnsEmpty() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product product = Product.builder()
                .id("id1")
                .name("A")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt)
                .modifiedDate(createdAt)
                .build();

        productService.addProduct(product);

        var result = productService.getModifiedProducts();
        assertTrue(result.isEmpty());
    }

    /** getCategoriesWithProducts method */
    @Test
    void getCategoriesWithProducts_success_returnsDistinctOnlyPresentCategories() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product book1 = Product.builder()
                .id("id1")
                .name("Book 1")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt)
                .modifiedDate(createdAt)
                .build();

        Product book2 = Product.builder()
                .id("id2")
                .name("Book 2")
                .category(Category.BOOKS)
                .rating(6)
                .createdDate(createdAt.plusMinutes(1))
                .modifiedDate(createdAt.plusMinutes(1))
                .build();

        Product toy1 = Product.builder()
                .id("id3")
                .name("Toy 1")
                .category(Category.TOYS)
                .rating(7)
                .createdDate(createdAt.plusMinutes(2))
                .modifiedDate(createdAt.plusMinutes(2))
                .build();

        productService.addProduct(book1);
        productService.addProduct(book2);
        productService.addProduct(toy1);

        var categories = productService.getCategoriesWithProducts();

        assertEquals(Set.of(Category.BOOKS, Category.TOYS), new HashSet<>(categories));
        assertFalse(categories.contains(Category.ELECTRONICS));
    }

    @Test
    void getCategoriesWithProducts_emptyService_returnsEmptyList() {
        var categories = productService.getCategoriesWithProducts();
        assertTrue(categories.isEmpty());
    }

    /** countProductsInCategory method */
    @Test
    void countProductsInCategory_success_returnsNumberOfProductsInACategory() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product book1 = Product.builder()
                .id("id1")
                .name("Book 1")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(createdAt)
                .modifiedDate(createdAt)
                .build();

        Product book2 = Product.builder()
                .id("id2")
                .name("Book 2")
                .category(Category.BOOKS)
                .rating(6)
                .createdDate(createdAt.plusMinutes(1))
                .modifiedDate(createdAt.plusMinutes(1))
                .build();

        Product toy1 = Product.builder()
                .id("id3")
                .name("Toy 1")
                .category(Category.TOYS)
                .rating(6)
                .createdDate(createdAt.plusMinutes(2))
                .modifiedDate(createdAt.plusMinutes(2))
                .build();

        productService.addProduct(book1);
        productService.addProduct(book2);
        productService.addProduct(toy1);

        var booksTotal = productService.countProductsInCategory(Category.BOOKS);
        var toysTotal = productService.countProductsInCategory(Category.TOYS);

        assertEquals(2, booksTotal);
        assertEquals(1, toysTotal);
    }

    @Test
    void countProductsInCategory_nullCategory_throwsNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> productService.countProductsInCategory(null));
        assertEquals("category", ex.getMessage());
    }

    /** getProductInitialsMap method */
    @Test
    void getProductInitialsMap_success_countsUppercased_andIsUnmodifiable() {
        LocalDateTime t = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product a1 = Product.builder()
                .id("id1")
                .name("alpha")
                .category(Category.BOOKS)
                .rating(5)
                .createdDate(t)
                .modifiedDate(t)
                .build();

        Product a2 = Product.builder()
                .id("id2")
                .name("Apple")
                .category(Category.TOYS)
                .rating(6)
                .createdDate(t.plusMinutes(1))
                .modifiedDate(t.plusMinutes(1))
                .build();

        Product b1 = Product.builder()
                .id("id3")
                .name("banana")
                .category(Category.FOOD)
                .rating(7)
                .createdDate(t.plusMinutes(2))
                .modifiedDate(t.plusMinutes(2))
                .build();

        productService.addProduct(a1);
        productService.addProduct(a2);
        productService.addProduct(b1);

        var initials = productService.getProductInitialsMap();

        assertEquals(2, initials.get('A'));
        assertEquals(1, initials.get('B'));
    }

    @Test
    void getProductInitialsMap_empty_returnsEmptyMap() {
        assertTrue(productService.getProductInitialsMap().isEmpty());
    }

    /** getTopRatedProductsThisMonth method */
    @Test
    void getTopRatedProductsThisMonth_success_returnsTopRatedThisMonth_sortedByNewestFirst() {
        // Fixed clock in @BeforeEach makes "this month" = 2025-09 (Europe/Stockholm)
        LocalDateTime aug31 = LocalDateTime.of(2025, 8, 31, 12, 0);
        LocalDateTime sep01 = LocalDateTime.of(2025, 9, 1, 9, 0);
        LocalDateTime sep02 = LocalDateTime.of(2025, 9, 2, 10, 0);
        LocalDateTime sep03 = LocalDateTime.of(2025, 9, 3, 11, 0);
        LocalDateTime sep04 = LocalDateTime.of(2025, 9, 4, 12, 0);

        Product productOld = Product.builder()
                .id("id1")
                .name("Old product")
                .category(Category.BOOKS)
                .rating(10)
                .createdDate(aug31)
                .modifiedDate(aug31)
                .build();

        Product product1Low = Product.builder()
                .id("id2")
                .name("new product low rated")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(sep01)
                .modifiedDate(sep01)
                .build();

        Product product2Top = Product.builder()
                .id("id3")
                .name("new product top rated")
                .category(Category.TOYS)
                .rating(9)
                .createdDate(sep02)
                .modifiedDate(sep02)
                .build();

        Product product3Top = Product.builder()
                .id("id4")
                .name("new product top rated")
                .category(Category.FOOD)
                .rating(9)
                .createdDate(sep03)
                .modifiedDate(sep03)
                .build();

        Product product4Low = Product.builder()
                .id("id5")
                .name("new product low rated")
                .category(Category.BOOKS)
                .rating(8)
                .createdDate(sep04)
                .modifiedDate(sep04)
                .build();

        productService.addProduct(productOld);
        productService.addProduct(product1Low);
        productService.addProduct(product2Top);
        productService.addProduct(product3Top);
        productService.addProduct(product4Low);

        var result = productService.getTopRatedProductsThisMonth();

        assertEquals(List.of(product3Top, product2Top), result);
    }

    @Test
    void getTopRatedProductsThisMonth_noProductsInCurrentMonth_returnsEmptyList() {
        LocalDateTime aug30 = LocalDateTime.of(2025, 8, 30, 12, 0);
        LocalDateTime aug31 = LocalDateTime.of(2025, 8, 31, 12, 0);

        Product product1Old = Product.builder()
                .id("id1")
                .name("Old product top rated")
                .category(Category.BOOKS)
                .rating(9)
                .createdDate(aug30)
                .modifiedDate(aug30)
                .build();

        Product product2Old = Product.builder()
                .id("id2")
                .name("Old product low rated")
                .category(Category.TOYS)
                .rating(8)
                .createdDate(aug31)
                .modifiedDate(aug31)
                .build();

        productService.addProduct(product1Old);
        productService.addProduct(product2Old);

        var result = productService.getTopRatedProductsThisMonth();

        assertTrue(result.isEmpty());
    }
}
