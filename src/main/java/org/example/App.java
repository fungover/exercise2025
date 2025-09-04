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

// Products created today.
        warehouse.addProduct(Warehouse.createProduct("1", "Gaming Laptop", Category.ELECTRONICS, 9));
        warehouse.addProduct(Warehouse.createProduct("2", "New Running Shoes", Category.SPORTS, 8));
        warehouse.addProduct(Warehouse.createProduct("11", "Tennis Racket", Category.SPORTS, 8));
        warehouse.addProduct(Warehouse.createProduct("13", "Organic Coffee", Category.FOOD, 9));

// Products created 1 day ago.
        warehouse.addProduct(Warehouse.createOldProduct("15", "Smartphone", Category.ELECTRONICS, 10, 1));

// Products created 2 days ago.
        warehouse.addProduct(Warehouse.createOldProduct("14", "Chocolate Bar", Category.FOOD, 6, 2));

// Products created 3 days ago.
        warehouse.addProduct(Warehouse.createOldProduct("3", "Java Book", Category.BOOKS, 7, 3));
        warehouse.addProduct(Warehouse.createOldProduct("4", "Gaming Mouse", Category.ELECTRONICS, 8, 3));

// Products created 5 days ago.
        warehouse.addProduct(Warehouse.createOldProduct("9", "Winter Jacket", Category.CLOTHING, 7, 5));

// Products created 7 days ago.
        warehouse.addProduct(Warehouse.createOldProduct("5", "JavaScript Book", Category.BOOKS, 6, 7));
        warehouse.addProduct(Warehouse.createOldProduct("6", "Old T-shirt", Category.CLOTHING, 5, 7));

// Products created 10 days ago.
        warehouse.addProduct(Warehouse.createOldProduct("10", "Office Chair", Category.FURNITURE, 6, 10));

// Products created 12 days ago.
        warehouse.addProduct(Warehouse.createOldProduct("16", "Python Book", Category.BOOKS, 8, 12));

// Products created 14 days ago.
        warehouse.addProduct(Warehouse.createOldProduct("12", "LEGO Set", Category.TOYS, 9, 14));

// Produkter skapade för 30 dagar sedan.
        warehouse.addProduct(Warehouse.createOldProduct("7", "Ancient Furniture", Category.FURNITURE, 4, 30));

// Products created with specific dates.

        warehouse.addProduct(Warehouse.createProductWithDate("17", "Summer Dress", Category.CLOTHING, 7,
                LocalDate.of(2025, 6, 15)));

        warehouse.addProduct(Warehouse.createProductWithDate("8", "Christmas Toy", Category.TOYS, 10,
                LocalDate.of(2024, 12, 24)));


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
        System.out.println("\n=== CATEGORIES WITH PRODUCT: " + categories + " ===");

        long electronicsCount = warehouse.countProductsInCategory(Category.ELECTRONICS);
        System.out.println("\n=== NUMBER OF PRODUCTS IN ELECTRONICS: " + electronicsCount + " ===");

        Map<Character, Integer> initials = warehouse.getProductInitialsMap();
        System.out.println("\n=== NUMBER OF FIRST CHARACTER OCCURENCES IN EACH PRODUCT: " + initials + " ===");
    }
}
