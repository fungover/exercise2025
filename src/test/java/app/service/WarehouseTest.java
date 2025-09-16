package app.service;

import app.entities.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static app.entities.Category.FOOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseTest {

    @Test
    public void addProduct() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product("0","Bread",FOOD,5));
        List<Product> products = warehouse.products();


        assertThat(products)
                .hasSize(1)
                .contains(new Product("0","Bread",FOOD,5,products.getFirst().createdDate(), products.getFirst().modifiedDate()));

        System.out.println("Added "+products.size()+" Items to the warehouse");
        System.out.println("ID: "+products.getFirst().ID());
        System.out.println("Name: "+products.getFirst().name());
        System.out.println("Category: "+products.getFirst().category());
        System.out.println("Rating: "+products.getFirst().rating());
        System.out.println("Created date: "+products.getFirst().createdDate());
        System.out.println("Modified date: "+products.getFirst().modifiedDate());
    }






}
