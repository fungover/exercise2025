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
        warehouse.addProduct(new Product(0,"Bread",FOOD,5));
        List<Product> products = warehouse.products();


        assertThat(products)
                .hasSize(1)
                .contains(new Product(0,"Bread",FOOD,5,products.getFirst().createdDate(), products.getFirst().modifiedDate()));

        System.out.println("Added "+products.size()+" Items to the warehouse");
        System.out.println("ID: "+products.getFirst().ID());
        System.out.println("Name: "+products.getFirst().name());
        System.out.println("Category: "+products.getFirst().category());
        System.out.println("Rating: "+products.getFirst().rating());
        System.out.println("Created date: "+products.getFirst().createdDate());
        System.out.println("Modified date: "+products.getFirst().modifiedDate());
    }

    @Test
    public void updateProduct() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product(0,"Bread",FOOD,5));
        System.out.println("Added "+warehouse.products().size()+" Items to the warehouse");
        System.out.println("ID: "+warehouse.products().get(0).ID());
        System.out.println("Name: "+warehouse.products().get(0).name());
        System.out.println("Category: "+warehouse.products().get(0).category());
        System.out.println("Rating: "+warehouse.products().get(0).rating());

        System.out.println();
        warehouse.updateProduct(0,"Milk",FOOD,7);



        assertEquals(1,warehouse.products().size());
        assertEquals("Milk",warehouse.products().get(0).name());
        System.out.println("Updated "+warehouse.products().size()+" Items in the warehouse");
        System.out.println("ID: "+warehouse.products().get(0).ID());
        System.out.println("Name: "+warehouse.products().get(0).name());
        System.out.println("Rating: "+warehouse.products().get(0).rating());
    }

    @Test
    public void getAllProducts() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product(0,"Bread",FOOD,5));
        warehouse.addProduct(new Product(1,"Milk",FOOD,7));
        List<Product> products = warehouse.getAllProducts();

        assertThat(products)
                .hasSize(2)
                .contains(new Product(0,"Bread",FOOD,5,products.getFirst().createdDate(), products.getFirst().modifiedDate()),
                        new Product(1,"Milk",FOOD,7,products.get(1).createdDate(), products.get(1).modifiedDate()));

        System.out.println("Contains "+products.size()+" Items");
        System.out.println("ID: "+products.get(0).ID() + " Name: "+products.get(0).name());
        System.out.println("ID: "+products.get(1).ID() + " Name: "+products.get(1).name());
    }

    @Test
    void getProductByID() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product(0,"Bread",FOOD,5));
        warehouse.addProduct(new Product(1,"Milk",FOOD,7));
        warehouse.addProduct(new Product(2,"Coffee",FOOD,3));

        Product product = warehouse.getProductByID(2);
        assertEquals(2,product.ID());
        assertEquals("Coffee",product.name());
        assertEquals(FOOD,product.category());
        assertEquals(3,product.rating());

        System.out.println("Found product info: "+ warehouse.getProductByID(2));
    }


}
