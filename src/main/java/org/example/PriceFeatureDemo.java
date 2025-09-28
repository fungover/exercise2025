package org.example;

import org.example.entities.Category;
import org.example.entities.Product;

import java.math.BigDecimal;

public class PriceFeatureDemo {
    public static void main(String[] args) {
        System.out.println("💰 Price Feature Demo");
        System.out.println("====================");

        // -- Product with explicit price--
        System.out.println("\n📱 Example 1: Product with explicit price");
        Product iPhone = new Product.Builder()
            .id("IPHONE001")
            .name("iPhone 15 Pro")
            .category(Category.ELECTRONICS)
            .rating(9)
            .price(new BigDecimal("999.99"))
            .build();

        System.out.println("Product: " + iPhone);
        System.out.println("Price: $" + iPhone.price());

        // --Product with default price--
        System.out.println("\n🎮 Example 2: Product with default price");
        Product freeGame = new Product.Builder()
            .id("GAME001")
            .name("Free Game")
            .category(Category.GAMES)
            .rating(7)
            .build();

        System.out.println("Product: " + freeGame);
        System.out.println("Price: $" + freeGame.price());

        // --Price validation--
        System.out.println("\n❌ Example 3: Price validation");
        try {
            Product invalidProduct = new Product.Builder()
                .id("INVALID001")
                .name("Invalid Product")
                .category(Category.ELECTRONICS)
                .rating(5)
                .price(new BigDecimal("-10.00"))
                .build();
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Validation works: " + e.getMessage());
        }

        // --Different price formats--
        System.out.println("\n💻 Example 4: Different price formats");
        Product laptop = new Product.Builder()
            .id("LAPTOP001")
            .name("MacBook Pro")
            .category(Category.ELECTRONICS)
            .rating(10)
            .price(new BigDecimal("2499.00"))
            .build();

        Product book = new Product.Builder()
            .id("BOOK001")
            .name("Java Programming Book")
            .category(Category.MOVIES)
            .rating(8)
            .price(new BigDecimal("29.99"))
            .build();

        System.out.println("Expensive: " + laptop.name() + " - $" + laptop.price());
        System.out.println("Affordable: " + book.name() + " - $" + book.price());

        // --Price comparison--
        System.out.println("\n📊 Example 5: Price comparison");
        System.out.println("iPhone vs MacBook:");
        System.out.println("iPhone price: $" + iPhone.price());
        System.out.println("MacBook price: $" + laptop.price());
        System.out.println("MacBook is more expensive: " +
            (laptop.price().compareTo(iPhone.price()) > 0));

        System.out.println("\n🎉 Price feature successfully added to Product!");
    }
}
