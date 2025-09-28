package org.example;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;

// ----Builder Pattern in action----
public class BuilderPatternDemo {
    public static void main(String[] args) {
        System.out.println("🏗️ Builder Pattern Demo for Product Class");
        System.out.println("==========================================");

        // method chaining
        System.out.println("\n📱 Example 1: Creating a product with all fields specified:");
        Product product1 = new Product.Builder()
            .id("PROD001")
            .name("iPhone 15 Pro")
            .category(Category.ELECTRONICS)
            .rating(9)
            .createdDate(LocalDate.of(2025, 9, 1))
            .modifiedDate(LocalDate.of(2025, 9, 15))
            .build();

        System.out.println("Product: " + product1);

        //automatic date setting
        System.out.println("\n🎮 Example 2: Creating a product with automatic dates:");
        Product product2 = new Product.Builder()
            .id("PROD002")
            .name("Nintendo Switch")
            .category(Category.GAMES)
            .rating(8)
            .build();

        System.out.println("Product: " + product2);
        System.out.println("Created Date: " + product2.createdDate());
        System.out.println("Modified Date: " + product2.modifiedDate());

        //prevents direct constructor access
        System.out.println("\n Example 3: Constructor is private, only Builder can create products");
        System.out.println("// new Product(...) is not possible, compilation error!");
        System.out.println("// Only new Product.Builder()...build() works");

        //chaining in different order
        System.out.println("\n🚗 Example 4: Builder allows flexible method chaining:");
        Product product3 = new Product.Builder()
            .category(Category.CARS)
            .rating(7)
            .name("Tesla Model 3")
            .id("PROD003")
            .build();

        System.out.println("Product: " + product3);

        //Validation
        System.out.println("\n Example 5: Validation example this will throw an exception:");
        try {
            Product invalidProduct = new Product.Builder()
                .id("PROD004")
                .name("") // Empty name should cause validation error
                .category(Category.ELECTRONICS)
                .rating(5)
                .build();
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Validation works: " + e.getMessage());
        }

        System.out.println("\n🎉 Builder Pattern implementation is working perfectly!");
    }
}