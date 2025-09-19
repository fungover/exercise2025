package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse warehouse;
    private Clock fixed;

    @BeforeEach
    void setup() {
        // Europe/Stockholm, 2025-09-08 12:00 local (10:00Z)
        fixed = Clock.fixed(Instant.parse("2025-09-08T10:00:00Z"), ZoneId.of("Europe/Stockholm"));
        warehouse = new Warehouse(fixed);
    }

    /** getAllProducts method */
    @Test
    void getAllProducts_returnsEmptyWhenNothingAdded() {
        var all = warehouse.getAllProducts();
        assertTrue(all.isEmpty());
    }

    @Test
    void getAllProducts_listsTwoAfterAddingTwo() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);

        Product testProduct1 = Product.createNew("id1", "Product1", Category.BOOKS, 7, createdAt1);
        Product testProduct2 = Product.createNew("id2", "Product2", Category.ELECTRONICS, 8, createdAt2);

        warehouse.addProduct(testProduct1);
        warehouse.addProduct(testProduct2);

        var all = warehouse.getAllProducts();
        assertEquals(2, all.size());
        assertTrue(all.contains(testProduct1));
        assertTrue(all.contains(testProduct2));
    }

    /** addProduct method */
    @Test
    void addProduct_success_addsToWarehouse() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);
        Product testProduct = Product.createNew("id1", "Product1", Category.BOOKS, 7, createdAt);

        warehouse.addProduct(testProduct);

        var all = warehouse.getAllProducts();
        assertEquals(1, all.size());
        assertTrue(all.contains(testProduct));
    }

    @Test
    void addProduct_duplicateId_throwsIllegalArgumentException() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);

        Product testProduct1 = Product.createNew("id1", "Product1", Category.BOOKS, 5, createdAt1);
        Product testProduct2 = Product.createNew("id1", "Product2", Category.TOYS, 8, createdAt2);

        warehouse.addProduct(testProduct1);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(testProduct2));
        assertTrue(exception.getMessage().contains("duplicate id"));
    }

    /** getProductById method */
    @Test
    void getProductById_success_getCorrectProductById() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);
        Product testProduct = Product.createNew("id1", "Product1", Category.BOOKS, 7, createdAt);
        warehouse.addProduct(testProduct);

        var testProductById = warehouse.getProductById(testProduct.id());

        assertEquals(Optional.of(testProduct), testProductById);
    }

    @Test
    void getProductById_nullId_throwsNullPointerException() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> warehouse.getProductById(null));
        assertTrue(exception.getMessage().contains("id"));
    }

    @Test
    void getProductById_blankId_throwsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> warehouse.getProductById("  "));
        assertTrue(exception.getMessage().contains("id required"));
    }

    /** updateProduct method */
    @Test
    void updateProduct_success_updatesFields_andPersists() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 9, 0);
        Product original = Product.createNew("id1", "Original", Category.BOOKS, 5, createdAt);
        warehouse.addProduct(original);

        Product updated = warehouse.updateProduct("id1", "New Name", Category.TOYS, 9);

        assertEquals("id1", updated.id());
        assertEquals("New Name", updated.name());
        assertEquals(Category.TOYS, updated.category());
        assertEquals(9, updated.rating());
        assertEquals(createdAt, updated.createdDate());
        assertEquals(LocalDateTime.of(2025, 9, 8, 12, 0), updated.modifiedDate());
        assertEquals(Optional.of(updated), warehouse.getProductById("id1"));
    }

    @Test
    void updateProduct_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> warehouse.updateProduct(null, "test", Category.BOOKS, 7));
    }

    @Test
    void updateProduct_blankId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> warehouse.updateProduct("   ", "test", Category.BOOKS, 7));
    }

    @Test
    void updateProduct_missingId_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class,
                () -> warehouse.updateProduct("missing-id", "test", Category.BOOKS, 7));
    }

    /** getProductsByCategorySorted method */
    @Test
    void getProductsByCategorySorted_success_filtersAndSorts() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);
        LocalDateTime createdAt3 = LocalDateTime.of(2025, 9, 3, 12, 0);

        Product testProduct1 = Product.createNew("id1", "C", Category.BOOKS, 5, createdAt1);
        Product testProduct2 = Product.createNew("id2", "B", Category.TOYS, 8, createdAt2);
        Product testProduct3 = Product.createNew("id3", "A", Category.TOYS, 8, createdAt3);

        warehouse.addProduct(testProduct1);
        warehouse.addProduct(testProduct2);
        warehouse.addProduct(testProduct3);

        var result = warehouse.getProductsByCategorySorted(Category.TOYS);

        assertEquals(List.of("A", "B"),
                result.stream().map(Product::name).toList());
    }

    @Test
    void getProductsByCategorySorted_noProductsInCategory_returnsEmptyList() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);

        Product product1 = Product.createNew("id1", "A", Category.BOOKS, 5, createdAt1);
        Product product2 = Product.createNew("id2", "B", Category.TOYS,  8, createdAt2);

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        var result = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);

        assertTrue(result.isEmpty());
    }


    @Test
    void getProductsByCategorySorted_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> warehouse.getProductsByCategorySorted(null));
    }

    /** getProductsCreatedAfter method */
    @Test
    void getProductsCreatedAfter_success_filtersStrictlyAfterDate() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);
        LocalDateTime createdAt3 = LocalDateTime.of(2025, 9, 3, 12, 0);

        Product product1 = Product.createNew("id1", "sep01",  Category.BOOKS, 5, createdAt1);
        Product product2 = Product.createNew("id2", "sep02", Category.BOOKS, 5, createdAt2);
        Product product3 = Product.createNew("id3", "sep03",  Category.BOOKS, 5, createdAt3);
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);

        var result = warehouse.getProductsCreatedAfter(LocalDate.of(2025, 9, 2));

        assertEquals(List.of("sep03"), result.stream().map(Product::name).toList());
    }

    @Test
    void getProductsCreatedAfter_noMatches_returnsEmpty() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

       Product product = Product.createNew("id1", "sep01", Category.BOOKS, 5, createdAt);

       warehouse.addProduct(product);

        var result = warehouse.getProductsCreatedAfter(LocalDate.of(2025, 9, 1));
        assertTrue(result.isEmpty());
    }

    @Test
    void getProductsCreatedAfter_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> warehouse.getProductsCreatedAfter(null));
    }

    /** getModifiedProducts method */
    @Test
    void getModifiedProducts_success_returnsOnlyUpdatedOnes() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 10, 0);
        Product product1 = Product.createNew("id1", "A", Category.BOOKS, 5, createdAt);
        Product product2 = Product.createNew("id2", "B", Category.BOOKS, 5, createdAt);

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        warehouse.updateProduct("id2", "updated product", Category.BOOKS, 7);

        var result = warehouse.getModifiedProducts();
        assertEquals(List.of("id2"), result.stream().map(Product::id).toList());
    }

    @Test
    void getModifiedProducts_noneModified_returnsEmpty() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product product = Product.createNew("id1", "A", Category.BOOKS, 5, createdAt);

        warehouse.addProduct(product);

        var result = warehouse.getModifiedProducts();
        assertTrue(result.isEmpty());
    }

    /** getCategoriesWithProducts method */
    @Test
    void getCategoriesWithProducts_success_returnsDistinctOnlyPresentCategories() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product book1 = Product.createNew("id1", "Book 1", Category.BOOKS, 5, createdAt);
        Product book2 = Product.createNew("id2", "Book 2", Category.BOOKS, 6, createdAt.plusMinutes(1));
        Product toy1  = Product.createNew("id3", "Toy 1",  Category.TOYS,  7, createdAt.plusMinutes(2));

        warehouse.addProduct(book1);
        warehouse.addProduct(book2);
        warehouse.addProduct(toy1);

        var categories = warehouse.getCategoriesWithProducts();

        assertEquals(Set.of(Category.BOOKS, Category.TOYS), new HashSet<>(categories));
        assertFalse(categories.contains(Category.ELECTRONICS));
    }

    @Test
    void getCategoriesWithProducts_emptyWarehouse_returnsEmptyList() {
        var categories = warehouse.getCategoriesWithProducts();
        assertTrue(categories.isEmpty());
    }

    /** countProductsInCategory method */
    @Test
    void countProductsInCategory_success_returnsNumberOfProductsInACategory() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product book1 = Product.createNew("id1", "Book 1", Category.BOOKS, 5, createdAt);
        Product book2 = Product.createNew("id2", "Book 2", Category.BOOKS, 6, createdAt.plusMinutes(1));
        Product toy1 = Product.createNew("id3", "Toy 1", Category.TOYS, 6, createdAt.plusMinutes(2));


        warehouse.addProduct(book1);
        warehouse.addProduct(book2);
        warehouse.addProduct(toy1);

        var booksTotal = warehouse.countProductsInCategory(Category.BOOKS);
        var toysTotal = warehouse.countProductsInCategory(Category.TOYS);

        assertEquals(2, booksTotal);
        assertEquals(1, toysTotal);
    }

    @Test
    void countProductsInCategory_nullCategory_throwsNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> warehouse.countProductsInCategory(null));
        assertEquals("category", ex.getMessage());
    }

    /** getProductInitialsMap method */
    @Test
    void getProductInitialsMap_success_countsUppercased_andIsUnmodifiable() {
        LocalDateTime t = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product a1 = Product.createNew("id1", "alpha",  Category.BOOKS, 5, t);
        Product a2 = Product.createNew("id2", "Apple",  Category.TOYS,  6, t.plusMinutes(1));
        Product b1 = Product.createNew("id3", "banana", Category.FOOD,  7, t.plusMinutes(2));

        warehouse.addProduct(a1);
        warehouse.addProduct(a2);
        warehouse.addProduct(b1);

        var initials = warehouse.getProductInitialsMap();

        assertEquals(2, initials.get('A'));
        assertEquals(1, initials.get('B'));
    }

    @Test
    void getProductInitialsMap_empty_returnsEmptyMap() {
        assertTrue(warehouse.getProductInitialsMap().isEmpty());
    }

    /** getTopRatedProductsThisMonth method */
    @Test
    void getTopRatedProductsThisMonth_success_returnsTopRatedThisMonth_sortedByNewestFirst() {
        // Fixed clock in @BeforeEach makes "this month" = 2025-09 (Europe/Stockholm)
        LocalDateTime aug31 = LocalDateTime.of(2025, 8, 31, 12, 0);
        LocalDateTime sep01 = LocalDateTime.of(2025, 9, 1,  9,  0);
        LocalDateTime sep02 = LocalDateTime.of(2025, 9, 2, 10,  0);
        LocalDateTime sep03 = LocalDateTime.of(2025, 9, 3, 11,  0);
        LocalDateTime sep04 = LocalDateTime.of(2025, 9, 4, 12,  0);

        Product productOld = Product.createNew("id1", "Old product", Category.BOOKS, 10, aug31);
        Product product1Low = Product.createNew("id2", "new product low rated", Category.BOOKS, 7, sep01);
        Product product2Top = Product.createNew("id3", "new product top rated", Category.TOYS,  9, sep02);
        Product product3Top = Product.createNew("id4", "new product top rated", Category.FOOD,  9, sep03);
        Product product4Low = Product.createNew("id5", "new product low rated", Category.BOOKS, 8, sep04);

        warehouse.addProduct(productOld);
        warehouse.addProduct(product1Low);
        warehouse.addProduct(product2Top);
        warehouse.addProduct(product3Top);
        warehouse.addProduct(product4Low);

        var result = warehouse.getTopRatedProductsThisMonth();

        assertEquals(List.of(product3Top, product2Top), result);
    }

    @Test
    void getTopRatedProductsThisMonth_noProductsInCurrentMonth_returnsEmptyList() {
        LocalDateTime aug30 = LocalDateTime.of(2025, 8, 30, 12, 0);
        LocalDateTime aug31 = LocalDateTime.of(2025, 8, 31, 12, 0);

        Product product1Old = Product.createNew("id1", "Old product top rated", Category.BOOKS, 9, aug30);
        Product product2Old = Product.createNew("id2", "Old product low rated", Category.TOYS,  8, aug31);
        warehouse.addProduct(product1Old);
        warehouse.addProduct(product2Old);

        var result = warehouse.getTopRatedProductsThisMonth();

        assertTrue(result.isEmpty());
    }
}
