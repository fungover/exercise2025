package org.example;

import org.example.service.Warehouse;

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
    }
}
