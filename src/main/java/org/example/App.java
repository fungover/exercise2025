package org.example;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {

        Product laptop = new Product(
                "1",
                "Gaming Laptop",
                Category.ELECTRONICS,
                9,
                LocalDate.now(),
                LocalDate.now()
        );

        System.out.println("Product created");
        System.out.println("ID: " + laptop.id());
        System.out.println("Name: " + laptop.name());
        System.out.println("Category: " + laptop.category());
        System.out.println("Rating: " + laptop.rating());
        System.out.println("Created Date: " + laptop.createdDate());

        System.out.println("\n whole product details: " + laptop);

        Product same = new Product("1", "Gaming Laptop", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now());
        System.out.println("laptop equals same: " + laptop.equals(same));

        Product other = new Product("2", "Gaming laptop", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now());
        System.out.println("laptop equals other: " + laptop.equals(other));
    }
}
