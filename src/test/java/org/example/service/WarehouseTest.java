package org.example.service;

import org.example.entities.Product;
import org.example.entities.Category;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


 class WarehouseTest {
     // --- addProduct ---
     @Test
     void addProductShouldAddSuccessfully() {
     Warehouse warehouse = new Warehouse();
     Product p = new Product(
             "1",
             "Kiwi",
             Category.Food,
             8,
             LocalDate.now(),
             LocalDate.now()
     );
     warehouse.addProduct(p);
     assertEquals(1, warehouse.getAllProducts().size());
     }

     @Test
     void addProductShouldThrowExceptionIfNameEmpty() {
         Warehouse warehouse = new Warehouse();
         Product p = new Product(
                 "2",
                 "",
                 Category.Food,
                 5,
                 LocalDate.now(),
                 LocalDate.now()
         );
         assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(p));
     }

     // --- updateProduct ---
     @Test
     void updateProductShouldUpdateSuccessfully() {
         Warehouse warehouse = new Warehouse();
         Product p = new Product(
                 "4",
                 "Milk",
                 Category.Food,
                 7,
                 LocalDate.now(),
                 LocalDate.now()
         );
         warehouse.addProduct(p);

         warehouse.updateProduct("4", "Apple", Category.Food, 9);

         Product updated = warehouse.getProductById("4");
         assertEquals("Apple", updated.name());
         assertEquals(9, updated.rating());
     }

     // --- getProductById ---
     @Test
     void getProductByIdShouldReturnProductWhenIdExists(){
         Warehouse warehouse = new Warehouse();
         Product p = new Product(
                 "1",
                 "Kiwi",
                 Category.Food,
                 8,
                 LocalDate.now(),
                 LocalDate.now()
         );
         warehouse.addProduct(p);
         Product product = warehouse.getProductById("1");
         assertEquals(p, product);
     }
     @Test
     void getProductByIdShouldThrowExceptionWhenIdDoesNotExist(){
         Warehouse warehouse = new Warehouse();
         assertThrows(IllegalArgumentException.class, () -> warehouse.getProductById("85"));
     }

     // --- getProductsByCategorySorted ---
     @Test
     void getProductsByCategorySortedShouldReturnSortedProductsByCategory(){
         Warehouse w = new Warehouse();
         w.addProduct(new Product("a", "Orange", Category.Food, 5, LocalDate.now(), LocalDate.now()));
         w.addProduct(new Product("b", "Kiwi", Category.Food, 8, LocalDate.now(), LocalDate.now()));
         w.addProduct(new Product("c", "Apple", Category.Drink, 7, LocalDate.now(), LocalDate.now()));
         w.addProduct(new Product("d", "Milk", Category.Drink, 9, LocalDate.now(), LocalDate.now()));

         var foods = w.getProductsByCategorySorted(Category.Food);
         assertEquals(2, foods.size());
         assertEquals("Kiwi", foods.get(0).name());
         assertEquals("Orange", foods.get(1).name());

     }
     @Test
     void getProductsByCategorySortedShouldThrowExceptionWhenCategoryIsNull(){
         Warehouse w = new Warehouse();
         assertThrows(IllegalArgumentException.class, () -> w.getProductsByCategorySorted(null));
     }
     // --- getProductsCreatedAfter ---
    @Test
     void getProductsCreatedAfterShouldFilter() {
         Warehouse w = new Warehouse();
         LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
         LocalDate yesterday = LocalDate.now().minusDays(1);
         LocalDate today = LocalDate.now();

         w.addProduct(new Product("x", "X", Category.Food, 5, fiveDaysAgo, fiveDaysAgo));
         w.addProduct(new Product("y", "Y", Category.Drink, 5, yesterday, yesterday));
         w.addProduct(new Product("z", "Z", Category.Drink, 5, today, today));

         var result = w.getProductsCreatedAfter(LocalDate.now().minusDays(2));
         assertEquals(2, result.size()); // Yesterday and Today
    }
    @Test
     void getProductsCreatedAfterShouldThrowExceptionWhenDateIsNull() {
        Warehouse w = new Warehouse();
        assertThrows(IllegalArgumentException.class, () -> w.getProductsCreatedAfter(null));
     }

     // --- getModifiedProducts ---
     @Test
     void getModifiedProductsShouldReturnModified() {
         Warehouse w = new Warehouse();
         LocalDate now = LocalDate.now();
         LocalDate yesterday = now.minusDays(1);

         // Not modified (createdDate == modifiedDate)
         w.addProduct(new Product("m1", "M1", Category.Food, 5, now, now));
         // Modified (createdDate != modifiedDate)
         w.addProduct(new Product("m2", "M2", Category.Drink, 5, now, yesterday));

         var modified = w.getModifiedProducts();
         assertEquals(1, modified.size());
         assertEquals("m2", modified.get(0).id());
     }
     @Test
     void getModifiedProductsShouldReturnEmptyListWhenNoModified() {
         Warehouse w = new Warehouse();
         LocalDate today = LocalDate.now();

         w.addProduct(new Product("n1", "N1", Category.Food, 5, today, today));
         w.addProduct(new Product("n2", "N2", Category.Drink, 5, today, today));

         var modified = w.getModifiedProducts();
         assertTrue(modified.isEmpty());
     }
 }
