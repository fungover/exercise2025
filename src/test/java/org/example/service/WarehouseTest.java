package org.example.service;

import org.example.entities.Product;
import org.example.entities.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


 class WarehouseTest {
     // --- addProduct ---
     @Test
     void addProductShouldAddSuccessfully() {
         Warehouse warehouse = new Warehouse();
         Product product = new Product.Builder()
                 .id("1")
                 .name("Kiwi")
                 .category(Category.Food)
                 .rating(8)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         warehouse.addProduct(product);
         assertEquals(1, warehouse.getAllProducts().size());
     }

     @Test
     void addProductShouldThrowExceptionIfNameEmpty() {
         Warehouse warehouse = new Warehouse();
         Product product = new Product.Builder()
                 .id("2")
                 .name("")
                 .category(Category.Food)
                .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(product));
     }

     @Test
     void addProductShouldThrowExceptionIfRatingOutOfRange() {
         Warehouse warehouse = new Warehouse();
         Product product = new Product.Builder()
                 .id("3")
                 .name("Pear")
                 .category(Category.Food)
                 .rating(11)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(product));
     }

     // --- updateProduct ---
     @Test
     void updateProductShouldUpdateSuccessfully() {
         Warehouse warehouse = new Warehouse();
         Product product = new Product.Builder()
                 .id("4")
                 .name("Milk")
                 .category(Category.Drink)
                 .rating(7)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         warehouse.addProduct(product);

         warehouse.updateProduct("4", "Apple", Category.Food, 9);

         Product updated = warehouse.getProductById("4");
         assertEquals("Apple", updated.name());
         assertEquals(9, updated.rating());
     }

     // --- getProductById ---
     @Test
     void getProductByIdShouldReturnProductWhenIdExists() {
         Warehouse warehouse = new Warehouse();
         Product p = new Product.Builder()
                 .id("1")
                 .name("Kiwi")
                 .category(Category.Food)
                 .rating(8)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         warehouse.addProduct(p);
         Product product = warehouse.getProductById("1");
         assertEquals(p, product); // p = product
     }

     @Test
     void getProductByIdShouldThrowExceptionWhenIdDoesNotExist() {
         Warehouse warehouse = new Warehouse();
         assertThrows(IllegalArgumentException.class, () -> warehouse.getProductById("85"));
     }

     // --- getProductsByCategorySorted ---
     @Test
     void getProductsByCategorySortedShouldReturnSortedProductsByCategory() {
         Warehouse warehouse = new Warehouse();
         warehouse.addProduct(
                 new Product.Builder()
                 .id("a")
                 .name("Orange")
                 .category(Category.Food)
                 .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());
         warehouse.addProduct(
                 new Product.Builder()
                 .id("b")
                 .name("Kiwi")
                 .category(Category.Food)
                 .rating(8)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());
         warehouse.addProduct(
                 new Product.Builder()
                 .id("c")
                 .name("Apple")
                 .category(Category.Drink)
                 .rating(7)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());
         warehouse.addProduct(
                 new Product.Builder()
                 .id("d")
                 .name("Milk")
                 .category(Category.Drink)
                 .rating(9)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());

         var foods = warehouse.getProductsByCategorySorted(Category.Food);
         assertEquals(2, foods.size());
         assertEquals("Kiwi", foods.get(0).name());
         assertEquals("Orange", foods.get(1).name());

     }

     @Test
     void getProductsByCategorySortedShouldThrowExceptionWhenCategoryIsNull() {
         Warehouse warehouse = new Warehouse();
         assertThrows(IllegalArgumentException.class, () -> warehouse.getProductsByCategorySorted(null));
     }

     // --- getProductsCreatedAfter ---
     @Test
     void getProductsCreatedAfterShouldFilter() {
         Warehouse warehouse = new Warehouse();
         LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
         LocalDate yesterday = LocalDate.now().minusDays(1);
         LocalDate today = LocalDate.now();

         warehouse.addProduct(
                 new Product.Builder()
                 .id("x")
                 .name("X")
                 .category(Category.Food)
                 .rating(5).createdDate(fiveDaysAgo)
                 .modifiedDate(fiveDaysAgo)
                 .build());

         warehouse.addProduct(
                 new Product.Builder()
                 .id("y")
                 .name("Y")
                 .category(Category.Drink)
                 .rating(5).createdDate(yesterday)
                 .modifiedDate(yesterday)
                 .build());

         warehouse.addProduct(
                 new Product.Builder()
                 .id("z").name("Z")
                 .category(Category.Drink)
                 .rating(5).createdDate(today)
                 .modifiedDate(today)
                 .build()
         );

         var result = warehouse.getProductsCreatedAfter(LocalDate.now().minusDays(2));
         assertEquals(2, result.size()); // Yesterday and Today
     }

     @Test
     void getProductsCreatedAfterShouldThrowExceptionWhenDateIsNull() {
         Warehouse warehouse = new Warehouse();
         assertThrows(IllegalArgumentException.class, () -> warehouse.getProductsCreatedAfter(null));
     }

     // --- getModifiedProducts ---
     @Test
     void getModifiedProductsShouldReturnModified() {
         Warehouse warehouse = new Warehouse();
         LocalDate now = LocalDate.now();
         LocalDate yesterday = now.minusDays(1);

         // Not modified (createdDate == modifiedDate)
         warehouse.addProduct(
                 new Product.Builder()
                         .id("m1")
                         .name("M1")
                         .category(Category.Food)
                         .rating(5)
                         .createdDate(now)
                         .modifiedDate(now)
                         .build());
         // Modified (createdDate != modifiedDate)
         warehouse.addProduct(
                 new Product.Builder()
                         .id("m2")
                         .name("M2")
                         .category(Category.Drink)
                         .rating(5)
                         .createdDate(now)
                         .modifiedDate(yesterday)
                         .build());

         var modified = warehouse.getModifiedProducts();
         assertEquals(1, modified.size());
         assertEquals("m2", modified.get(0).id());
     }

     @Test
     void getModifiedProductsShouldReturnEmptyListWhenNoModified() {
         Warehouse warehouse = new Warehouse();
         LocalDate today = LocalDate.now();

         warehouse.addProduct(new Product.Builder()
                 .id("n1")
                 .name("N1")
                 .category(Category.Food)
                 .rating(5)
                 .createdDate(today)
                 .modifiedDate(today)
                 .build());

         warehouse.addProduct(
                 new Product.Builder()
                         .id("n2")
                         .name("N2")
                         .category(Category.Drink)
                         .rating(5)
                         .createdDate(today)
                         .modifiedDate(today)
                         .build());

         var modified = warehouse.getModifiedProducts();
         assertTrue(modified.isEmpty());
     }

     // --- getAllProducts ---
     @Test
     void getAllProductsShouldReturnDefensiveCopy() {
         Warehouse warehouse = new Warehouse();
         Product product = new Product.Builder()
                 .id("10")
                 .name("A")
                 .category(Category.Food)
                 .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         warehouse.addProduct(product);

         var copy = warehouse.getAllProducts();
         assertEquals(1, copy.size());
         copy.clear();
         assertEquals(1, warehouse.getAllProducts().size());
     }

     // --- updateProduct failures ---
     @Test
     void updateProductShouldThrowExceptionWhenIdDoesNotExist() {
         Warehouse warehouse = new Warehouse();
         assertThrows(IllegalArgumentException.class, () ->
                 warehouse.updateProduct("missing", "Name", Category.Food, 5));
     }

     @Test
     void updateProductShouldValidateInput() {
         Warehouse warehouse = new Warehouse();
         // Valid product first
         Product product = new Product.Builder()
                 .id("u1")
                 .name("Coffee")
                 .category(Category.Drink)
                 .rating(6)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();
         warehouse.addProduct(product);

         // Empty id
         assertThrows(IllegalArgumentException.class, () ->
                 warehouse.updateProduct("", "Coffee", Category.Drink, 6));
         // Empty name
         assertThrows(IllegalArgumentException.class, () ->
                 warehouse.updateProduct("u1", "", Category.Drink, 6));
         //Null category
         assertThrows(IllegalArgumentException.class, () ->
                 warehouse.updateProduct("u1", "Coffee", null, 6));
         // Rating out of range
         assertThrows(IllegalArgumentException.class, () ->
                 warehouse.updateProduct("u1", "Coffee", Category.Drink, -1));
         assertThrows(IllegalArgumentException.class, () ->
                 warehouse.updateProduct("u1", "Coffee", Category.Drink, 11));

     }

     // --- getCategoriesWithProducts ---
     @Test
     void getCategoriesWithProductsShouldReturnSortedCategories() {
         Warehouse warehouse = new Warehouse();
         warehouse.addProduct(new Product.Builder()
                 .id("c1")
                 .name("Water")
                 .category(Category.Drink)
                 .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());

         warehouse.addProduct(new Product.Builder()
                 .id("c2")
                 .name("Bread")
                 .category(Category.Food)
                 .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());

         var categories = warehouse.getCategoriesWithProducts();
         assertEquals(2, categories.size());
         assertTrue(categories.contains(Category.Drink));
         assertTrue(categories.contains(Category.Food));
     }

     // --- countProductsInCategory ---
     @Test
     void countProductsInCategoryShouldCount() {
         Warehouse warehouse = new Warehouse();
         warehouse.addProduct(new Product.Builder()
                 .id("f1")
                 .name("Apple")
                 .category(Category.Food)
                 .rating(7)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());
         warehouse.addProduct(new Product.Builder()
                 .id("f2")
                 .name("Banana")
                 .category(Category.Food)
                 .rating(6)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());
         warehouse.addProduct(new Product.Builder()
                 .id("d1")
                 .name("Cola")
                 .category(Category.Drink)
                 .rating(6)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());

         long count = warehouse.countProductsInCategory(Category.Food);
         assertEquals(2, count);
     }

     @Test
     void countProductsInCategoryShouldThrowOnNull() {
         Warehouse warehouse = new Warehouse();
         assertThrows(IllegalArgumentException.class, () -> warehouse.countProductsInCategory(null));
     }

     // --- getProductInitialsMap ---
     @Test
     void getProductInitialsMapShouldCountInitialsCaseInsensitive() {
         Warehouse warehouse = new Warehouse();
         warehouse.addProduct(new Product.Builder()
                 .id("i1")
                 .name("apple")
                 .category(Category.Food)
         .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());
         warehouse.addProduct(new Product.Builder()
                 .id("i2")
                 .name("Avocado")
                 .category(Category.Food)
         .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());
         warehouse.addProduct(new Product.Builder()
                 .id("i3")
                 .name("banana")
                 .category(Category.Food)
         .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());

         var map = warehouse.getProductInitialsMap();
         assertEquals(2, map.get('A'));
         assertEquals(1, map.get('B'));
         assertFalse(map.containsKey('C'));
     }

     // --- getTopRatedProductsThisMonth ---
     @Test
     void getTopRatedProductsThisMonthShouldReturnTopRatedCreatedThisMonthSortedNewestFirst() {
         Warehouse warehouse = new Warehouse();
         LocalDate now = LocalDate.now();
         LocalDate earlierThisMonth = now.minusDays(5);
         LocalDate lastMonth = now.minusMonths(1);

         warehouse.addProduct(new Product.Builder()
                 .id("t1")
                 .name("A")
                 .category(Category.Food)
                 .rating(9)
                 .createdDate(earlierThisMonth)
                 .modifiedDate(earlierThisMonth)
                 .build());
         warehouse.addProduct(new Product.Builder()
                 .id("t2")
                 .name("B")
                 .category(Category.Food)
         .rating(10)
                 .createdDate(now)
                 .modifiedDate(now)
                 .build());
         warehouse.addProduct(new Product.Builder()
                 .id("t3")
                 .name("C")
                 .category(Category.Food)
         .rating(10)
                 .createdDate(earlierThisMonth)
                 .modifiedDate(earlierThisMonth)
                 .build());
         warehouse.addProduct(new Product.Builder()
                 .id("t4")
                 .name("D")
                 .category(Category.Food)
                 .rating(10)
                 .createdDate(lastMonth)
                 .modifiedDate(lastMonth)
                 .build());

         var result = warehouse.getTopRatedProductsThisMonth();

         assertEquals(2, result.size());
         // New first
         assertEquals("B", result.get(0).name());
         assertEquals("C", result.get(1).name());

     }
 }
