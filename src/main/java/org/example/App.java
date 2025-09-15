package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class App {
    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        // Products created today using builder pattern.
        warehouse.addProduct(new Product.Builder()
                .id("1")
                .name("Gaming Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .build());

        warehouse.addProduct(new Product.Builder()
                .id("2")
                .name("New Running Shoes")
                .category(Category.SPORTS)
                .rating(8)
                .build());

        warehouse.addProduct(new Product.Builder()
                .id("11")
                .name("Tennis Racket")
                .category(Category.SPORTS)
                .rating(8)
                .build());

        warehouse.addProduct(new Product.Builder()
                .id("13")
                .name("Organic Coffee")
                .category(Category.FOOD)
                .rating(9)
                .build());

        // Products created 1 day ago
        LocalDate oneDayAgo = LocalDate.now().minusDays(1);
        warehouse.addProduct(new Product.Builder()
                .id("15")
                .name("Smartphone")
                .category(Category.ELECTRONICS)
                .rating(10)
                .createdDate(oneDayAgo)
                .modifiedDate(oneDayAgo)
                .build());

        warehouse.addProduct(new Product.Builder()
                .id("16")
                .name("ipad")
                .category(Category.ELECTRONICS)
                .rating(10)
                .createdDate(oneDayAgo)
                .modifiedDate(oneDayAgo)
                .build());

        // Products created 2 days ago
        LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
        warehouse.addProduct(new Product.Builder()
                .id("14")
                .name("Chocolate Bar")
                .category(Category.FOOD)
                .rating(6)
                .createdDate(twoDaysAgo)
                .modifiedDate(twoDaysAgo)
                .build());

        warehouse.addProduct(new Product.Builder()
                .id("17")
                .name("Free Candy!")
                .category(Category.FOOD)
                .rating(10)
                .createdDate(twoDaysAgo)
                .modifiedDate(twoDaysAgo)
                .build());

        // Products created 3 days ago
        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        warehouse.addProduct(new Product.Builder()
                .id("3")
                .name("Java Book")
                .category(Category.BOOKS)
                .rating(10)
                .createdDate(threeDaysAgo)
                .modifiedDate(threeDaysAgo)
                .build());

        warehouse.addProduct(new Product.Builder()
                .id("4")
                .name("Gaming Mouse")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(threeDaysAgo)
                .modifiedDate(threeDaysAgo)
                .build());

        // Products created 5 days ago
        LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
        warehouse.addProduct(new Product.Builder()
                .id("9")
                .name("Winter Jacket")
                .category(Category.CLOTHING)
                .rating(7)
                .createdDate(fiveDaysAgo)
                .modifiedDate(fiveDaysAgo)
                .build());

        // Products created 7 days ago
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        warehouse.addProduct(new Product.Builder()
                .id("5")
                .name("JavaScript Book")
                .category(Category.BOOKS)
                .rating(6)
                .createdDate(sevenDaysAgo)
                .modifiedDate(sevenDaysAgo)
                .build());

        warehouse.addProduct(new Product.Builder()
                .id("6")
                .name("Old T-shirt")
                .category(Category.CLOTHING)
                .rating(5)
                .createdDate(sevenDaysAgo)
                .modifiedDate(sevenDaysAgo)
                .build());

        // Products created 10 days ago
        LocalDate tenDaysAgo = LocalDate.now().minusDays(10);
        warehouse.addProduct(new Product.Builder()
                .id("10")
                .name("Office Chair")
                .category(Category.FURNITURE)
                .rating(6)
                .createdDate(tenDaysAgo)
                .modifiedDate(tenDaysAgo)
                .build());

        // Products created 12 days ago
        LocalDate twelveDaysAgo = LocalDate.now().minusDays(12);
        warehouse.addProduct(new Product.Builder()
                .id("18")
                .name("Python Book")
                .category(Category.BOOKS)
                .rating(8)
                .createdDate(twelveDaysAgo)
                .modifiedDate(twelveDaysAgo)
                .build());

        // Products created 14 days ago
        LocalDate fourteenDaysAgo = LocalDate.now().minusDays(14);
        warehouse.addProduct(new Product.Builder()
                .id("12")
                .name("LEGO Set")
                .category(Category.TOYS)
                .rating(9)
                .createdDate(fourteenDaysAgo)
                .modifiedDate(fourteenDaysAgo)
                .build());

        // Products created 30 days ago
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        warehouse.addProduct(new Product.Builder()
                .id("7")
                .name("Ancient Furniture")
                .category(Category.FURNITURE)
                .rating(4)
                .createdDate(thirtyDaysAgo)
                .modifiedDate(thirtyDaysAgo)
                .build());

        // Products created with specific dates
        warehouse.addProduct(new Product.Builder()
                .id("19")
                .name("Summer Dress")
                .category(Category.CLOTHING)
                .rating(7)
                .createdDate(LocalDate.of(2025, 6, 15))
                .modifiedDate(LocalDate.of(2025, 6, 15))
                .build());

        warehouse.addProduct(new Product.Builder()
                .id("8")
                .name("Christmas Toy")
                .category(Category.TOYS)
                .rating(10)
                .createdDate(LocalDate.of(2024, 12, 24))
                .modifiedDate(LocalDate.of(2024, 12, 24))
                .build());

        System.out.println("\n=== ALL PRODUCTS ===");
        warehouse.getAllProducts().forEach(System.out::println);

        System.out.println("\n=== GETTING A PRODUCT BY ID ===");
        Optional<Product> found = warehouse.getProductById("1");
        System.out.println("Product with ID 1: " + found);

        Optional<Product> notFound = warehouse.getProductById("999");
        System.out.println("Product with ID 999: " + notFound);

        System.out.println("\n=== ELECTRONICS (SORTED BY NAME) ===");
        List<Product> electronics = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);
        electronics.forEach(System.out::println);

        System.out.println("\n=== PRODUCTS CREATED AFTER YESTERDAY ===");
        List<Product> recentProducts = warehouse.getProductsCreatedAfter(LocalDate.now().minusDays(1));
        recentProducts.forEach(System.out::println);

        warehouse.updateProduct("3", "Updated Java Book", Category.BOOKS, 10);

        System.out.println("\n=== MODIFIED PRODUCTS ===");
        List<Product> modified = warehouse.getModifiedProducts();
        modified.forEach(System.out::println);

        System.out.println("\n=== VG ASSIGNMENT TEST ===");

        Set<Category> categories = warehouse.getCategoriesWithProducts();
        System.out.println("\n=== CATEGORIES WITH PRODUCTS: " + categories + " ===");

        long electronicsCount = warehouse.countProductsInCategory(Category.ELECTRONICS);
        System.out.println("\n=== NUMBER OF PRODUCTS IN ELECTRONICS: " + electronicsCount + " ===");

        Map<Character, Integer> initials = warehouse.getProductInitialsMap();
        System.out.println("\n=== NUMBER OF FIRST CHARACTER OCCURRENCES IN EACH PRODUCT: " + initials + " ===");

        System.out.println("\n=== TOP RATED PRODUCTS THIS MONTH ===");
        List<Product> topRated = warehouse.getTopRatedProductsThisMonth();

        if (topRated.isEmpty()) {
            System.out.println("No products found this month.");
        } else {
            topRated.forEach(product -> System.out.println(product.name() + " (Rating: " + product.rating() + ", Created: " + product.createdDate() + ")"));
        }
    }
}
