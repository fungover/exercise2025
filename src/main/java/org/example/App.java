package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;

import java.time.LocalDateTime;

public class App {
    static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        warehouse.addProduct(new Product("1", "Cereal",
                Category.PROVISIONS, 7, LocalDateTime.now(), null));
    }
}
