package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class App {
    public static void main(String[] args) {

        ProductRepository repo = new InMemoryProductRepository();
        ProductService productService = new ProductService(repo);

        // Add some demo products
        Product chewBone = new Product.Builder()
                .id(UUID.randomUUID().toString())
                .name("Chew Bone")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(ZonedDateTime.now().minusDays(5))
                .modifiedDate(ZonedDateTime.now().minusDays(5))
                .build();

        Product squeakyBall = new Product.Builder()
                .id(UUID.randomUUID().toString())
                .name("Squeaky Ball")
                .category(Category.TOYS)
                .rating(6)
                .createdDate(ZonedDateTime.now().minusDays(2))
                .modifiedDate(ZonedDateTime.now().minusDays(2))
                .build();

        Product comfyBed = new Product.Builder()
                .id(UUID.randomUUID().toString())
                .name("Comfy Dog Bed")
                .category(Category.BEDDING)
                .rating(9)
                .createdDate(ZonedDateTime.now().minusDays(10))
                .modifiedDate(ZonedDateTime.now().minusDays(3)) // modified later
                .build();

        productService.addProduct(chewBone);
        productService.addProduct(squeakyBall);
        productService.addProduct(comfyBed);

        // Show all products
        System.out.println("\nAll products in warehouse:");
        productService.getAllProducts().forEach(System.out::println);

        // Show sorted products by category
        System.out.println("\nCategory toys sorted by name:");
        productService.getProductsByCategorySorted(Category.TOYS)
                .forEach(System.out::println);

        // Get product by ID
        System.out.println("\nFetch product by ID:");
        Optional<Product> fetched = productService.getProductById(squeakyBall.id());
        fetched.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Product not found")
        );

        // Show modified products
        System.out.println("\nShow modified products:");
        List<Product> modified = productService.getModifiedProducts();
        modified.forEach(System.out::println);

        //Update product
        System.out.println("\nUpdating product rating and name...");
        productService.updateProduct(squeakyBall.id(), "Super Squeaky Ball", Category.TOYS, 10);
        productService.getProductById(squeakyBall.id())
                .ifPresent(System.out::println);

        // Products created AFTER a given date
        System.out.println("\nProducts added in the last 3 days:");
        ZonedDateTime threeDaysAgo = ZonedDateTime.now().minusDays(3);
        productService.getProductsCreatedAfter(threeDaysAgo)
                .forEach(System.out::println);
    }
}
