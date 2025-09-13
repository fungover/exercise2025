package org.example;

import org.example.service.Warehouse;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class WarehouseCLI {
    private final Warehouse warehouse;
    private final Scanner scanner;

    public WarehouseCLI(Warehouse warehouse, Scanner scanner) {
        this.warehouse = warehouse;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            displayMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> addProduct();
                case "2" -> updateProduct();
                case "3" -> listAllProducts();
                case "4" -> getProductById();
                case "5" -> listProductsByCategory();
                case "6" -> listProductsCreatedAfter();
                case "7" -> listModifiedProducts();
                case "8" -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid option. Please enter 1-8.");
                }
            }
        }

    private void displayMenu() {
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. List All Products");
        System.out.println("4. Get Product By ID");
        System.out.println("5. List Products By Category");
        System.out.println("6. List Products Created After");
        System.out.println("7. List Modified Products");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addProduct() {
        try {
            System.out.print("Enter product ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter product name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter product category: ");
            String category = scanner.nextLine().trim();
            System.out.print("Enter product rating: ");
            int rating = scanner.nextInt();
            System.out.print("Enter product release date (YYYY-MM-DD): ");
            String listedDate = scanner.nextLine().trim();
            System.out.print("Enter product last update date (YYYY-MM-DD): ");
            String lastUpdateDate = scanner.nextLine().trim();
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
