package org.example;

import org.example.decorator.DiscountDecorator;
import org.example.decorator.Sellable;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

public class App {
    public static void main(String[] args) {

        /**
         *
         * EXAMPLE USAGE
         * - We create ProductRepository first (InMemory implementation).
         * - Then we inject it into ProductService.
         * <p>
         * WHY THIS ORDER?
         * <p>
         * - ProductService depends on ProductRepository (needs it to function).
         * - Dependencies must be created before the object that uses them.
         * - This is called "Dependency Injection" (we inject the dependency from outside).
         */

        ProductRepository repository = new InMemoryProductRepository();
        ProductService productService = new ProductService(repository);

        // Products created today using builder pattern.
        Product laptop = new Product.Builder()
                .id("laptop-1")
                .name("Gaming Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(1000.0)
                .build();

        Product phone = new Product.Builder()
                .id("phone-1")
                .name("Smartphone")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(800.0)
                .build();

        Product book = new Product.Builder()
                .id("book-1")
                .name("Java Programming Book")
                .category(Category.BOOKS)
                .rating(10)
                .price(45.99)
                .build();

        productService.addProduct(laptop);
        productService.addProduct(phone);
        productService.addProduct(book);

        System.out.println("=== DECORATOR PATTERN DEMONSTRATION ===\n");

        // =================================================================
        // TEST 1: Basic Discount Decorator
        // =================================================================
        System.out.println("--- Test 1: Basic 20% Discount ---");

        /**
         * Here we decorate the laptop with DiscountDecorator to apply a 20% discount.
         * We use the exisiting laptop object and wrap it with the decorator.
         * this way we do not modify the original laptop object.
         *
         *  QUESTION FOR TEACHER:
         *  - If we lets say would add a simple christmas edition decorator. Would it not be better to NOT add the "String christmasDecorator"
         *  to the product interface? and just add it to the decorator class? So we don't modify the original interface?
         */

        Sellable discountedLaptop = new DiscountDecorator(laptop, 20.0);

        System.out.println("Original Laptop:");
        System.out.println("  Name: " + laptop.getName());
        System.out.println("  Price: $" + laptop.getPrice());

        System.out.println("With 20% Discount:");
        System.out.println("  Name: " + discountedLaptop.getName());
        System.out.println("  Price: $" + discountedLaptop.getPrice());
        System.out.println("  Savings: $" + (laptop.getPrice() - discountedLaptop.getPrice()));
        

    }
}
