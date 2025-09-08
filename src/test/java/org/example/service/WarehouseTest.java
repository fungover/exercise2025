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
     void addProductShouldThrowIfNameEmpty() {
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
}
