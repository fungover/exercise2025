package app;

import entities.Category;
import entities.Product;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Product coffee = new Product.Builder()
                .id("A-001")
                .name("Coffee")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Product lego = new Product.Builder()
                .id("B-001")
                .name("Lego")
                .category(Category.TOYS)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        System.out.println(coffee);
        System.out.println("DisplayName:" + coffee.getCategory().getDisplayName());

        System.out.println(lego);
        System.out.println("DisplayName:" + lego.getCategory().getDisplayName());
    }
}

