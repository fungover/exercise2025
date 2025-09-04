package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;

public class App {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        Product laptop = new Product("laptop", Category.ELECTRONICS, 4);
        Product lap22top = new Product("lapttttop", Category.ELECTRONICS, 4);

        System.out.println(warehouse.addProduct(laptop));
        System.out.println(warehouse.addProduct(lap22top));

        warehouse.getProducts();

        warehouse.updateProduct("1", "blueLaptop", Category.FOOD, 3);

        warehouse.getProducts();

    }
}
