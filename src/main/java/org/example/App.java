package org.example;

import org.example.entities.Category;
import org.example.entities.DiscountDecorator;
import org.example.entities.Product;
import org.example.entities.Sellable;

public class App {
    public static void main(String[] args) {
        Product Clue = new Product.Builder()
                .id("1")
                .name("Clue")
                .category(Category.BOARDGAMES)
                .rating(7)
                .price(39.99)
                .build();

        Sellable discountedClue = new DiscountDecorator(Clue, 20);

        System.out.println("Original price of Clue: " + Clue.getPrice());
        System.out.printf("Discounted price of Clue (20%% off): %.2f%n", discountedClue.getPrice());
    }
}
