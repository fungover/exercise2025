package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;

import java.time.LocalDate;
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
        System.out.println("6. List Products Created After Date");
        System.out.println("7. List Modified Products");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addProduct() {
        try {
            System.out.print("Enter ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Category (ELECTRONICS, BOOKS, CLOTHING, FOOD, TOYS): ");
            String categoryInput = scanner.nextLine().trim().toUpperCase();
            Category category = Category.valueOf(categoryInput);
            System.out.print("Enter Rating (0-10): ");
            int rating = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Created Date (YYYY-MM-DD): ");
            LocalDate createdDate = LocalDate.parse(scanner.nextLine().trim());
            Product product = new Product(id, name, category, rating, createdDate, createdDate);
            warehouse.addProduct(product);
            System.out.println("Product added: " + product);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateProduct() {
        try {
            System.out.print("Enter ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter New Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter New Category (ELECTRONICS, BOOKS, CLOTHING, FOOD, TOYS): ");
            String categoryInput = scanner.nextLine().trim().toUpperCase();
            Category category = Category.valueOf(categoryInput);
            System.out.print("Enter New Rating (0-10): ");
            int rating = Integer.parseInt(scanner.nextLine().trim());
            boolean updated = warehouse.updateProduct(id, name, category, rating);
            System.out.println(updated ? "Product updated" : "Product not found");
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
