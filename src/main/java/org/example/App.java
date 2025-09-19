package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        // Add some demo products
        Product chewBone = new Product(
                UUID.randomUUID().toString(),
                "Chew Bone",
                Category.FOOD,
                8,
                ZonedDateTime.now().minusDays(5),
                ZonedDateTime.now().minusDays(5)
        );

        Product squeakyBall = new Product(
                UUID.randomUUID().toString(),
                "Squeaky Ball",
                Category.TOYS,
                6,
                ZonedDateTime.now().minusDays(2),
                ZonedDateTime.now().minusDays(2)
        );

        Product dogBed = new Product(
                UUID.randomUUID().toString(),
                "Comfy Dog Bed",
                Category.BEDDING,
                9,
                ZonedDateTime.now().minusDays(10),
                ZonedDateTime.now().minusDays(3) // modified later
        );

        warehouse.addProduct(chewBone);
        warehouse.addProduct(squeakyBall);
        warehouse.addProduct(dogBed);

        // Show all products
        System.out.println("\nAll products in warehouse:");
        warehouse.getAllProducts().forEach(System.out::println);

        // Show sorted products by category
        System.out.println("\nCategory toys sorted by name:");
        warehouse.getProductsByCategorySorted(Category.TOYS)
                .forEach(System.out::println);

        // Get product by ID
        System.out.println("\nFetch product by ID:");
        Optional<Product> fetched = warehouse.getProductById(squeakyBall.id());
        fetched.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Product not found")
        );

        // Show modified products
        System.out.println("\nShow modified products:");
        List<Product> modified = warehouse.getModifiedProducts();
        modified.forEach(System.out::println);

        // Update product
        System.out.println("\nUpdating product rating and name...");
        warehouse.updateProduct(squeakyBall.id(), "Super Squeaky Ball", Category.TOYS, 10);
        warehouse.getProductById(squeakyBall.id())
                .ifPresent(System.out::println);

        // Products created AFTER a given date
        System.out.println("\nProducts added in the last 3 days:");
        ZonedDateTime threeDaysAgo = ZonedDateTime.now().minusDays(3);
        warehouse.getProductsCreatedAfter(threeDaysAgo)
                .forEach(System.out::println);
    }
}
