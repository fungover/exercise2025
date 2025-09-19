package org.example.service;

import org.example.decorators.DiscountDecorator;
import org.example.entities.Product;
import org.example.entities.Category;
import org.example.entities.Sellable;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


 class ProductServiceTest {
     // --- addProduct ---
     @Test
     void addProductShouldAddSuccessfully() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         Product product = new Product.Builder()
                 .id("1")
                 .name("Kiwi")
                 .category(Category.Food)
                 .rating(8)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         productService.addProduct(product);
         assertEquals(1, productService.getAllProducts().size());
     }

     @Test
     void addProductShouldThrowExceptionIfNameEmpty() {
         assertThrows(IllegalArgumentException.class, () -> {
             new Product.Builder()
                     .id("2")
                     .name("")
                     .category(Category.Food)
                     .rating(5)
                     .createdDate(LocalDate.now())
                     .modifiedDate(LocalDate.now())
                     .build();
         });
     }

     @Test
     void addProductShouldThrowExceptionIfRatingOutOfRange() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         Product product = new Product.Builder()
                 .id("3")
                 .name("Pear")
                 .category(Category.Food)
                 .rating(11)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         assertThrows(IllegalArgumentException.class, () -> productService.addProduct(product));
     }

     // --- updateProduct ---
     @Test
     void updateProductShouldUpdateSuccessfully() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         Product product = new Product.Builder()
                 .id("4")
                 .name("Milk")
                 .category(Category.Drink)
                 .rating(7)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         productService.addProduct(product);

         productService.updateProduct("4", "Apple", Category.Food, 9);

         Product updated = productService.getProductById("4");
         assertEquals("Apple", updated.name());
         assertEquals(9, updated.rating());
     }

     // --- getProductById ---
     @Test
     void getProductByIdShouldReturnProductWhenIdExists() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         Product p = new Product.Builder()
                 .id("1")
                 .name("Kiwi")
                 .category(Category.Food)
                 .rating(8)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         productService.addProduct(p);
         Product product = productService.getProductById("1");
         assertEquals(p, product); // p = product
     }

     @Test
     void getProductByIdShouldThrowExceptionWhenIdDoesNotExist() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         assertThrows(IllegalArgumentException.class, () -> productService.getProductById("85"));
     }

     // --- getProductsByCategorySorted ---
     @Test
     void getProductsByCategorySortedShouldReturnSortedProductsByCategory() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         productService.addProduct(
                 new Product.Builder()
                 .id("a")
                 .name("Orange")
                 .category(Category.Food)
                 .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());
         productService.addProduct(
                 new Product.Builder()
                 .id("b")
                 .name("Kiwi")
                 .category(Category.Food)
                 .rating(8)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());
         productService.addProduct(
                 new Product.Builder()
                 .id("c")
                 .name("Apple")
                 .category(Category.Drink)
                 .rating(7)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());
         productService.addProduct(
                 new Product.Builder()
                 .id("d")
                 .name("Milk")
                 .category(Category.Drink)
                 .rating(9)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());

         var foods = productService.getProductsByCategorySorted(Category.Food);
         assertEquals(2, foods.size());
         assertEquals("Kiwi", foods.get(0).name());
         assertEquals("Orange", foods.get(1).name());

     }

     @Test
     void getProductsByCategorySortedShouldThrowExceptionWhenCategoryIsNull() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         assertThrows(IllegalArgumentException.class, () -> productService.getProductsByCategorySorted(null));
     }

     // --- getProductsCreatedAfter ---
     @Test
     void getProductsCreatedAfterShouldFilter() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
         LocalDate yesterday = LocalDate.now().minusDays(1);
         LocalDate today = LocalDate.now();

         productService.addProduct(
                 new Product.Builder()
                 .id("x")
                 .name("X")
                 .category(Category.Food)
                 .rating(5).createdDate(fiveDaysAgo)
                 .modifiedDate(fiveDaysAgo)
                 .build());

         productService.addProduct(
                 new Product.Builder()
                 .id("y")
                 .name("Y")
                 .category(Category.Drink)
                 .rating(5).createdDate(yesterday)
                 .modifiedDate(yesterday)
                 .build());

         productService.addProduct(
                 new Product.Builder()
                 .id("z").name("Z")
                 .category(Category.Drink)
                 .rating(5).createdDate(today)
                 .modifiedDate(today)
                 .build()
         );

         var result = productService.getProductsCreatedAfter(LocalDate.now().minusDays(2));
         assertEquals(2, result.size()); // Yesterday and Today
     }

     @Test
     void getProductsCreatedAfterShouldThrowExceptionWhenDateIsNull() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         assertThrows(IllegalArgumentException.class, () -> productService.getProductsCreatedAfter(null));
     }

     // --- getModifiedProducts ---
     @Test
     void getModifiedProductsShouldReturnModified() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         LocalDate now = LocalDate.now();
         LocalDate yesterday = now.minusDays(1);

         // Not modified (createdDate == modifiedDate)
         productService.addProduct(
                 new Product.Builder()
                         .id("m1")
                         .name("M1")
                         .category(Category.Food)
                         .rating(5)
                         .createdDate(now)
                         .modifiedDate(now)
                         .build());
         // Modified (createdDate != modifiedDate)
         productService.addProduct(
                 new Product.Builder()
                         .id("m2")
                         .name("M2")
                         .category(Category.Drink)
                         .rating(5)
                         .createdDate(now)
                         .modifiedDate(yesterday)
                         .build());

         var modified = productService.getModifiedProducts();
         assertEquals(1, modified.size());
         assertEquals("m2", modified.get(0).id());
     }

     @Test
     void getModifiedProductsShouldReturnEmptyListWhenNoModified() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         LocalDate today = LocalDate.now();

         productService.addProduct(new Product.Builder()
                 .id("n1")
                 .name("N1")
                 .category(Category.Food)
                 .rating(5)
                 .createdDate(today)
                 .modifiedDate(today)
                 .build());

         productService.addProduct(
                 new Product.Builder()
                         .id("n2")
                         .name("N2")
                         .category(Category.Drink)
                         .rating(5)
                         .createdDate(today)
                         .modifiedDate(today)
                         .build());

         var modified = productService.getModifiedProducts();
         assertTrue(modified.isEmpty());
     }

     // --- getAllProducts ---
     @Test
     void getAllProductsShouldReturnDefensiveCopy() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         Product product = new Product.Builder()
                 .id("10")
                 .name("A")
                 .category(Category.Food)
                 .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();

         productService.addProduct(product);

         var copy = productService.getAllProducts();
         assertEquals(1, copy.size());
         copy.clear();
         assertEquals(1, productService.getAllProducts().size());
     }

     // --- updateProduct failures ---
     @Test
     void updateProductShouldThrowExceptionWhenIdDoesNotExist() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         assertThrows(IllegalArgumentException.class, () ->
                 productService.updateProduct("missing", "Name", Category.Food, 5));
     }

     @Test
     void updateProductShouldValidateInput() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         // Valid product first
         Product product = new Product.Builder()
                 .id("u1")
                 .name("Coffee")
                 .category(Category.Drink)
                 .rating(6)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build();
         productService.addProduct(product);

         // Empty id
         assertThrows(IllegalArgumentException.class, () ->
                 productService.updateProduct("", "Coffee", Category.Drink, 6));
         // Empty name
         assertThrows(IllegalArgumentException.class, () ->
                 productService.updateProduct("u1", "", Category.Drink, 6));
         //Null category
         assertThrows(IllegalArgumentException.class, () ->
                 productService.updateProduct("u1", "Coffee", null, 6));
         // Rating out of range
         assertThrows(IllegalArgumentException.class, () ->
                 productService.updateProduct("u1", "Coffee", Category.Drink, -1));
         assertThrows(IllegalArgumentException.class, () ->
                 productService.updateProduct("u1", "Coffee", Category.Drink, 11));

     }

     // --- getCategoriesWithProducts ---
     @Test
     void getCategoriesWithProductsShouldReturnSortedCategories() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         productService.addProduct(new Product.Builder()
                 .id("c1")
                 .name("Water")
                 .category(Category.Drink)
                 .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                 .build());

         productService.addProduct(new Product.Builder()
                 .id("c2")
                 .name("Bread")
                 .category(Category.Food)
                 .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());

         var categories = productService.getCategoriesWithProducts();
         assertEquals(2, categories.size());
         assertTrue(categories.contains(Category.Drink));
         assertTrue(categories.contains(Category.Food));
     }

     // --- countProductsInCategory ---
     @Test
     void countProductsInCategoryShouldCount() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         productService.addProduct(new Product.Builder()
                 .id("f1")
                 .name("Apple")
                 .category(Category.Food)
                 .rating(7)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());
         productService.addProduct(new Product.Builder()
                 .id("f2")
                 .name("Banana")
                 .category(Category.Food)
                 .rating(6)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());
         productService.addProduct(new Product.Builder()
                 .id("d1")
                 .name("Cola")
                 .category(Category.Drink)
                 .rating(6)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());

         long count = productService.countProductsInCategory(Category.Food);
         assertEquals(2, count);
     }

     @Test
     void countProductsInCategoryShouldThrowOnNull() {
        ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         assertThrows(IllegalArgumentException.class, () -> productService.countProductsInCategory(null));
     }

     // --- getProductInitialsMap ---
     @Test
     void getProductInitialsMapShouldCountInitialsCaseInsensitive() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         productService.addProduct(new Product.Builder()
                 .id("i1")
                 .name("apple")
                 .category(Category.Food)
         .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());
         productService.addProduct(new Product.Builder()
                 .id("i2")
                 .name("Avocado")
                 .category(Category.Food)
         .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());
         productService.addProduct(new Product.Builder()
                 .id("i3")
                 .name("banana")
                 .category(Category.Food)
         .rating(5)
                 .createdDate(LocalDate.now())
                 .modifiedDate(LocalDate.now())
                         .build());

         var map = productService.getProductInitialsMap();
         assertEquals(2, map.get('A'));
         assertEquals(1, map.get('B'));
         assertFalse(map.containsKey('C'));
     }

     // --- getTopRatedProductsThisMonth ---
     @Test
     void getTopRatedProductsThisMonthShouldReturnTopRatedCreatedThisMonthSortedNewestFirst() {
         ProductRepository repo = new InMemoryProductRepository();
         ProductService productService = new ProductService(repo);
         LocalDate now = LocalDate.now();
         LocalDate earlierThisMonth = now.minusDays(5);
         LocalDate lastMonth = now.minusMonths(1);

         productService.addProduct(new Product.Builder()
                 .id("t1")
                 .name("A")
                 .category(Category.Food)
                 .rating(9)
                 .createdDate(earlierThisMonth)
                 .modifiedDate(earlierThisMonth)
                 .build());
         productService.addProduct(new Product.Builder()
                 .id("t2")
                 .name("B")
                 .category(Category.Food)
         .rating(10)
                 .createdDate(now)
                 .modifiedDate(now)
                 .build());
         productService.addProduct(new Product.Builder()
                 .id("t3")
                 .name("C")
                 .category(Category.Food)
         .rating(10)
                 .createdDate(earlierThisMonth)
                 .modifiedDate(earlierThisMonth)
                 .build());
         productService.addProduct(new Product.Builder()
                 .id("t4")
                 .name("D")
                 .category(Category.Food)
                 .rating(10)
                 .createdDate(lastMonth)
                 .modifiedDate(lastMonth)
                 .build());

         var result = productService.getTopRatedProductsThisMonth();

         assertEquals(2, result.size());
         // New first
         assertEquals("B", result.get(0).name());
         assertEquals("C", result.get(1).name());

     }

 }
