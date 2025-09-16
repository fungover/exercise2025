package app.service;

import app.entities.Category;
import app.entities.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static app.entities.Category.*;
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

    @Test
    void getWrongProductByID() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product(0,"Bread",FOOD,5));
        warehouse.addProduct(new Product(1,"Milk",FOOD,7));
        warehouse.addProduct(new Product(2,"Coffee",FOOD,3));
        Product product = warehouse.getProductByID(10);
        assertEquals(null,product);
    }

    @Test
    void getProductByCategory() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product(0,"Amiga 500",ELECTRONICS,5));
        warehouse.addProduct(new Product(1,"Playstation",ELECTRONICS,7));
        warehouse.addProduct(new Product(2,"Nintendo Wii",ELECTRONICS,10));
        warehouse.addProduct(new Product(3,"Nintendo 64",ELECTRONICS,10));
        warehouse.addProduct(new Product(4,"Xbox 360",ELECTRONICS,10));
        warehouse.addProduct(new Product(5,"Playstation 2",ELECTRONICS,10));
        warehouse.addProduct(new Product(6,"Xbox One",ELECTRONICS,10));
        warehouse.addProduct(new Product(7,"Playstation 4",ELECTRONICS,10));
        warehouse.addProduct(new Product(8,"Playstation 5",ELECTRONICS,10));
        warehouse.addProduct(new Product(9,"Xbox",ELECTRONICS,10));
        warehouse.addProduct(new Product(10,"Playstation 3",ELECTRONICS,10));
        warehouse.addProduct(new Product(11,"Phone",ELECTRONICS,7));
        warehouse.addProduct(new Product(12,"Tablet",ELECTRONICS,6));
        warehouse.addProduct(new Product(13,"Xbox Series X",ELECTRONICS,3));
        warehouse.addProduct(new Product(14,"Laptop",ELECTRONICS,5));
        warehouse.addProduct(new Product(15,"PC",ELECTRONICS,5));
        warehouse.addProduct(new Product(16,"Nintendo Switch",ELECTRONICS,10));
        warehouse.addProduct(new Product(17,"Dreamcast",ELECTRONICS,10));

        List<Product> updatedlist = warehouse.getProductsByCategory(Category.ELECTRONICS);
        assertThat(updatedlist).hasSize(18)
                .allMatch(product -> product.category() == ELECTRONICS);
        System.out.println("Found "+updatedlist.size()+" Electronics");
        updatedlist.forEach(product -> System.out.println("ID: "+product.ID() + " Name: "+product.name()));
    }



}
