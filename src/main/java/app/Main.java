package app;

import entities.Category;
import entities.Product;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Product coffee = new Product(
                "A-001", "coffee", Category.FOOD, 8, LocalDate.now(), LocalDate.now()
        );

        Product lego = new Product(
                "B-001", "LEGO Duplo", Category.TOYS, 9, LocalDate.now(), LocalDate.now()
        );

        System.out.println(coffee);
        System.out.println("DisplayName:" + coffee.category().getDisplayName());

        System.out.println(lego);
        System.out.println("DisplayName:" + lego.category().getDisplayName());
    }
}

