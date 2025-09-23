package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;
import org.example.entities.decorator.DiscountDecorator;
import org.example.entities.decorator.Sellable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ProductRepository repository = new InMemoryProductRepository();
        ProductService service = new ProductService(repository);
        Scanner scanner = new Scanner(System.in);

        // Lade till lite startdata i en separat metod
        seedData(service);

        // Lade till en map för att hantera aktiva rabatter
        Map<String, Double> activeDiscounts = new HashMap<>();

        // While loop för att hantera användarinput
        while (true) {
            System.out.println("\n--- My warehouse CLI ---");
            System.out.println("1. Add product");
            System.out.println("2. List all products");
            System.out.println("3. Delete product");
            System.out.println("4. Update product price");
            System.out.println("5. Apply discount");
            System.out.println("6. Remove discount");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number for the menu choice!");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter product id: ");
                    String id = scanner.nextLine().trim();
                    if (id.isBlank()) {
                        System.out.println("Product id cannot be blank!");
                        break;
                    }

                    System.out.print("Enter name: ");
                    String name = scanner.nextLine().trim();
                    if (name.isBlank()) {
                        System.out.println("Product name cannot be blank!");
                        break;
                    }

                    System.out.print("Enter category (TV, COMPUTER, PHONE, APPLIANCES): ");
                    String catInput = scanner.nextLine().trim().toUpperCase();
                    Category category;
                    try {
                        category = Category.valueOf(catInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid category!");
                        break;
                    }

                    System.out.print("Enter rating (0–10): ");
                    int rating;
                    try {
                        rating = Integer.parseInt(scanner.nextLine());
                        if (rating < 0 || rating > 10) {
                            System.out.println("Rating must be between 0 and 10!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Rating must be a number!");
                        break;
                    }

                    System.out.print("Enter price: ");
                    double price;
                    try {
                        price = Double.parseDouble(scanner.nextLine());
                        if (price < 0) {
                            System.out.println("Price cannot be negative!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Price must be a number!");
                        break;
                    }

                    try {
                        Product product = Product.builder()
                                .id(id)
                                .name(name)
                                .category(category)
                                .rating(rating)
                                .price(price)
                                .createdDate(LocalDate.now())
                                .modifiedDate(LocalDate.now())
                                .build();

                        service.addProduct(product);
                        System.out.println("Product added!");
                    } catch (Exception e) {
                        System.out.println("Could not add product: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("=== Products ===");
                    service.getAllProducts().forEach(p -> {
                        Double discount = activeDiscounts.get(p.id());
                        if (discount != null) {
                            Sellable discounted = new DiscountDecorator(p, discount);
                            System.out.println(p.id() + " - " + p.name() +
                                    " | Original: " + p.price() + " SEK" +
                                    " | Now: " + discounted.getPrice() + " SEK" +
                                    " (" + discount + "% off)");
                        } else {
                            System.out.println(p.id() + " - " + p.name() + " | Price: " + p.price() + " SEK");
                        }
                    });
                }
                case 3 -> {
                    System.out.print("Enter product id to delete: ");
                    String id = scanner.nextLine().trim();
                    if (id.isBlank()) {
                        System.out.println("Product id cannot be blank!");
                        break;
                    }

                    try {
                        Product product = service.getProductById(id);
                        service.deleteProduct(product);
                        System.out.println("Product deleted!");
                    } catch (Exception e) {
                        System.out.println("Could not delete: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.print("Enter product id to update price: ");
                    String id = scanner.nextLine().trim();
                    if (id.isBlank()) {
                        System.out.println("Product id cannot be blank!");
                        break;
                    }

                    Product product;
                    try {
                        product = service.getProductById(id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.print("Enter new price: ");
                    double newPrice;
                    try {
                        newPrice = Double.parseDouble(scanner.nextLine());
                        if (newPrice < 0) {
                            System.out.println("Price cannot be negative!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Price must be a number!");
                        break;
                    }

                    try {
                        Product updated = Product.builder()
                                .id(product.id())
                                .name(product.name())
                                .category(product.category())
                                .rating(product.rating())
                                .price(newPrice)
                                .createdDate(product.createdDate())
                                .modifiedDate(LocalDate.now())
                                .build();

                        service.updateProduct(updated);
                        System.out.println("Product price updated!");
                    } catch (Exception e) {
                        System.out.println("Could not update product: " + e.getMessage());
                    }
                }
                case 5 -> {
                    try {
                        System.out.print("Enter product id to apply discount: ");
                        String id = scanner.nextLine();
                        Product product = service.getProductById(id);

                        System.out.print("Enter discount %: ");
                        double discount = Double.parseDouble(scanner.nextLine());

                        activeDiscounts.put(id, discount);

                        Sellable discounted = new DiscountDecorator(product, discount);
                        System.out.println("Discount applied! New price: " + discounted.getPrice() + " SEK");
                    } catch (Exception e) {
                        System.out.println("Failed to apply discount: " + e.getMessage());
                    }
                }
                case 6 -> {
                    System.out.print("Enter product id to remove discount: ");
                    String id = scanner.nextLine();

                    if (activeDiscounts.containsKey(id)) {
                        activeDiscounts.remove(id);
                        System.out.println("Discount removed for product " + id);
                    } else {
                        System.out.println("No discount found for product " + id);
                    }
                }
                case 0 -> {
                    System.out.println("Welcome back!!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Metod för att ladda lite startdata
    private static void seedData(ProductService service) {
        LocalDate today = LocalDate.now();

        service.addProduct(Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .price(10000.0)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        service.addProduct(Product.builder()
                .id("p-002")
                .name("Budget Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .price(5000.0)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        service.addProduct(Product.builder()
                .id("p-003")
                .name("Pro Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .price(13000.0)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        service.addProduct(Product.builder()
                .id("p-004")
                .name("Another Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .price(8000.0)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        service.addProduct(Product.builder()
                .id("p-005")
                .name("Iphone")
                .category(Category.PHONE)
                .rating(7)
                .price(10000.0)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        service.addProduct(Product.builder()
                .id("p-006")
                .name("Refrigerator")
                .category(Category.APPLIANCES)
                .rating(7)
                .price(6000.0)
                .createdDate(today)
                .modifiedDate(today)
                .build());
    }
}