package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;

import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        // New products (created today)
        warehouse.addProduct(Warehouse.createProduct("1", "Gaming Laptop", Category.ELECTRONICS, 9));
        warehouse.addProduct(Warehouse.createProduct("2", "New Running Shoes", Category.SPORTS, 8));

        // Older products (3 days ago)
        warehouse.addProduct(Warehouse.createOldProduct("3", "Java Book", Category.BOOKS, 7, 3));
        warehouse.addProduct(Warehouse.createOldProduct("4", "Gaming Mouse", Category.ELECTRONICS, 8, 3));

        // older products (7 days ago)
        warehouse.addProduct(Warehouse.createOldProduct("5", "JavaScript Book", Category.BOOKS, 6, 7));
        warehouse.addProduct(Warehouse.createOldProduct("6", "Old T-shirt", Category.CLOTHING, 5, 7));

        // older products (30 days ago)
        warehouse.addProduct(Warehouse.createOldProduct("7", "Ancient Furniture", Category.FURNITURE, 4, 30));

        // specific date product (Christmas Toy, created on Dec 24, 2024)
        warehouse.addProduct(Warehouse.createProductWithDate("8", "Christmas Toy", Category.TOYS, 10,
                LocalDate.of(2024, 12, 24)));

        System.out.println("=== ALL PRODUCTS ===");
        warehouse.getAllProducts().forEach(System.out::println);

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
    }
}
