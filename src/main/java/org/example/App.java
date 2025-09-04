package org.example;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        try {

            Product okProduct = new Product("1", "Laptop", Category.ELECTRONICS, 8, LocalDate.now(), LocalDate.now());
            System.out.println("Created product: " + okProduct);

            Product badProduct = new Product("2", "   ", Category.ELECTRONICS, 8, LocalDate.now(), LocalDate.now());

        } catch (IllegalArgumentException e) {
            System.out.println("Error captured: " + e.getMessage());
        }
        try {
            // Detta borde krascha - dåligt rating
            Product badRating = new Product("3", "Laptop", Category.ELECTRONICS,
                    15, LocalDate.now(), LocalDate.now());
        } catch (IllegalArgumentException e) {
            System.out.println("Fel fångat: " + e.getMessage());
        }
    }
}
