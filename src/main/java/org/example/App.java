package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class App {
    private final ProductService productService;
    private final Scanner scanner;

    public App() {
        ProductRepository productRepository = new InMemoryProductRepository();
        this.productService = new ProductService(productRepository);
        this.scanner = new Scanner(System.in);
    }

    private void returnMessage() {
        System.out.println(" ");
        System.out.println("Press Enter key to return to main menu...");
        scanner.nextLine();
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
        System.out.println("");
        System.out.println("     Warehouse Management System");
        System.out.println("======================================");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. List All Products");
        System.out.println("4. Get Product By ID");
        System.out.println("5. List Products By Category");
        System.out.println("6. List Products Created After Date");
        System.out.println("7. List Modified Products");
        System.out.println("8. Exit");
        System.out.println("=======================================");
        System.out.println("Enter menu selection: ");
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
            Product product = new Product.Builder()
                    .id(id)
                    .name(name)
                    .category(category)
                    .rating(rating)
                    .createdDate(createdDate)
                    .build();

            productService.addProduct(product);
            System.out.println("Product added: " + product);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Use YYYY-MM-DD");
        }
        returnMessage();
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
            boolean updated = productService.updateProduct(id, name, category, rating);
            System.out.println(updated ? "Product updated" : "Product not found");
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        returnMessage();
    }

    private void printProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            products.forEach(System.out::println);
        }
    }

    private void listAllProducts() {
        List<Product> products = productService.getAllProducts();
        printProducts(products);
        returnMessage();
    }

    private void getProductById() {
        try {
            System.out.print("Enter ID: ");
            String id = scanner.nextLine().trim();
            Product product = productService.getProductById(id);
            if (product != null) {
                System.out.println("Product: " + product);
            } else {
                System.out.println("Product not found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        returnMessage();
    }

    private void listProductsByCategory() {
        try {
            System.out.print("Enter Category (ELECTRONICS, BOOKS, CLOTHING, FOOD, TOYS): ");
            String categoryInput = scanner.nextLine().trim().toUpperCase();
            Category category = Category.valueOf(categoryInput);
            List<Product> products = productService.getProductsByCategorySorted(category);
            printProducts(products);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid category type");
        }
        returnMessage();
    }

    private void listProductsCreatedAfter() {
        try {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine().trim());
            List<Product> products = productService.getProductsCreatedAfter(date);
            printProducts(products);
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format");
        }
        returnMessage();
    }

    private void listModifiedProducts() {
        List<Product> products = productService.getModifiedProducts();
        printProducts(products);
        returnMessage();
        
    }

    public static void main(String[] args) {
        new App().start();
    }
}
