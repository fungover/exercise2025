package app.service;

import app.entities.Category;
import app.entities.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static app.entities.Category.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    @Test
    public void addProduct() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product.Builder().name("Bread").category(FOOD).rating(5).build());
        List<Product> products = warehouse.products();


        assertThat(products)
                .hasSize(1)
                .contains(new Product.Builder().name("Bread").category(FOOD).rating(5).build());

        System.out.println("Added "+products.size()+" Items to the warehouse");
        System.out.println("ID: "+products.getFirst().ID());
        System.out.println("Name: "+products.getFirst().name());
        System.out.println("Category: "+products.getFirst().category());
        System.out.println("Rating: "+products.getFirst().rating());
        System.out.println("Created date: "+products.getFirst().createdDate());
        System.out.println("Modified date: "+products.getFirst().modifiedDate());
    }

    @Test
    public void addProductNoName() {
        Warehouse warehouse = new Warehouse();

        IllegalArgumentException fail = assertThrows(IllegalArgumentException.class,() ->
                warehouse.addProduct(new Product.Builder().category(FOOD).rating(5).build()));

        List<Product> products = warehouse.products();
        assertEquals("Product name cannot be null",fail.getMessage());
        System.out.println(fail.getMessage());
    }

    @Test
    public void addProductBlankName() {
        Warehouse warehouse = new Warehouse();

        IllegalArgumentException fail = assertThrows(IllegalArgumentException.class,() ->
                warehouse.addProduct(new Product.Builder().name("").category(FOOD).rating(5).build()));

        List<Product> products = warehouse.products();
        assertEquals("Product name cannot be blank",fail.getMessage());
        System.out.println(fail.getMessage());
    }

    @Test
    public void updateProduct() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product.Builder().name("Bread").category(FOOD).rating(5).build());
        System.out.println("Added "+warehouse.products().size()+" Items to the warehouse");
        System.out.println("ID: "+warehouse.products().getFirst().ID());
        System.out.println("Name: "+warehouse.products().getFirst().name());
        System.out.println("Category: "+warehouse.products().getFirst().category());
        System.out.println("Rating: "+warehouse.products().getFirst().rating());

        System.out.println();
        warehouse.updateProduct(0,"Milk",FOOD,7);

        assertEquals(1,warehouse.products().size());
        assertEquals("Milk",warehouse.products().getFirst().name());
        System.out.println("Updated "+warehouse.products().size()+" Items in the warehouse");
        System.out.println("ID: "+warehouse.products().getFirst().ID());
        System.out.println("Name: "+warehouse.products().getFirst().name());
        System.out.println("Rating: "+warehouse.products().getFirst().rating());
    }

    @Test
    public void updateProductFail() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product.Builder().name("Bread").category(FOOD).rating(5).build());

        assertThrows(IllegalArgumentException.class,
                () -> warehouse.updateProduct(1,"Milk",FOOD,7));
    }

    @Test
    public void getAllProducts() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product.Builder().name("Bread").category(FOOD).rating(5).build());
        warehouse.addProduct(new Product.Builder().name("Milk").category(FOOD).rating(7).build());
        List<Product> products = warehouse.getAllProducts();

        assertEquals(2,products.size());
        assertEquals("Bread",products.getFirst().name());
        assertEquals("Milk",products.get(1).name());

        System.out.println("Contains "+products.size()+" Items");
        System.out.println("ID: "+products.get(0).ID() + " Name: "+products.get(0).name());
        System.out.println("ID: "+products.get(1).ID() + " Name: "+products.get(1).name());
    }

    @Test
    public void getAllProductsEmpty() {
        Warehouse warehouse = new Warehouse();
        List<Product> products = warehouse.getAllProducts();
        assertThat(products).isEmpty();
        System.out.println("Contains "+products.size()+" Items");
    }

    @Test
    void getProductByID() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product.Builder().id(0).name("Bread").category(FOOD).rating(5).build());
        warehouse.addProduct(new Product.Builder().id(1).name("Milk").category(FOOD).rating(7).build());
        warehouse.addProduct(new Product.Builder().id(2).name("Coffee").category(FOOD).rating(3).build());

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
        warehouse.addProduct(new Product.Builder().id(0).name("Bread").category(FOOD).rating(5).build());
        warehouse.addProduct(new Product.Builder().id(1).name("Milk").category(FOOD).rating(7).build());
        warehouse.addProduct(new Product.Builder().id(2).name("Coffee").category(FOOD).rating(3).build());
        Product product = warehouse.getProductByID(10);
        assertNull(product);
    }

    @Test
    void getProductByCategory() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product.Builder().id(0).name("Amiga 500").category(ELECTRONICS).rating(5).build());
        warehouse.addProduct(new Product.Builder().id(1).name("Playstation").category(ELECTRONICS).rating(7).build());
        warehouse.addProduct(new Product.Builder().id(2).name("Nintendo Wii").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(3).name("Nintendo 64").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(4).name("Xbox 360").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(5).name("Playstation 2").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(6).name("Xbox One").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(7).name("Playstation 4").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(8).name("Playstation 5").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(9).name("Xbox").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(10).name("Playstation 3").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(11).name("Phone").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(12).name("Tablet").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(13).name("Xbox Series X").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(14).name("Laptop").category(ELECTRONICS).rating(5).build());
        warehouse.addProduct(new Product.Builder().id(15).name("PC").category(ELECTRONICS).rating(5).build());
        warehouse.addProduct(new Product.Builder().id(16).name("Nintendo Switch").category(ELECTRONICS).rating(10).build());
        warehouse.addProduct(new Product.Builder().id(17).name("Dreamcast").category(ELECTRONICS).rating(10).build());


        List<Product> updatedlist = warehouse.getProductsByCategory(Category.ELECTRONICS);
        assertThat(updatedlist).hasSize(18)
                .allMatch(product -> product.category() == ELECTRONICS);
        System.out.println("Found "+updatedlist.size()+" Electronics");
        updatedlist.forEach(product -> System.out.println("Name: "+product.name() + " ID: "+product.ID()));
    }

    @Test
    public void getProductsByCategoryEmpty() {
        Warehouse warehouse = new Warehouse();
        List<Product> updatedlist = warehouse.getProductsByCategory(Category.ELECTRONICS);
        assertThat(updatedlist).isEmpty();
        System.out.println("Found "+updatedlist.size()+" Electronics");
        updatedlist.forEach(product -> System.out.println("ID: "+product.ID() + " Name: "+product.name()));
    }

    @Test
    void getProductsCreatedAfter() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product(0,"Bread",FOOD,5,
                ZonedDateTime.of(2025, 2, 1, 12, 0, 0, 0,
                        ZoneId.of("Europe/Stockholm")), null));

        warehouse.addProduct(new Product(1,"Milk",FOOD,7,
                ZonedDateTime.of(2024, 1, 1, 12, 0, 0, 0,
                        ZoneId.of("Europe/Stockholm")), null));

        LocalDate cutoff = LocalDate.of(2025, 1, 1);
        List<Product> updatedlist = warehouse.getProductsCreatedAfter(cutoff);
        assertEquals(1,updatedlist.size());
        assertEquals("Bread",updatedlist.getFirst().name());

        System.out.println("Found "+updatedlist.size()+" Products created after "+cutoff);
        updatedlist.forEach(product -> System.out.println("ID: "+product.ID() + " Name: "+product.name()));
    }

    @Test
    public void getProductsCreatedAfterEmpty() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product(0,"Bread",FOOD,5,
                ZonedDateTime.of(2023, 2, 1, 12, 0, 0, 0,
                        ZoneId.of("Europe/Stockholm")), null));

        LocalDate cutoff = LocalDate.of(2025, 1, 1);
        List<Product> updatedlist = warehouse.getProductsCreatedAfter(cutoff);
        assertThat(updatedlist).isEmpty();
        System.out.println("Found "+updatedlist.size()+" Products created after "+cutoff);
    }

    @Test
    void getModifiedProducts() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product.Builder().id(0).name("Bread").category(FOOD).rating(5)
                .createdDate(ZonedDateTime.of(2025, 2, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .modifiedDate(ZonedDateTime.of(2025, 2, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .build());

        warehouse.addProduct(new Product.Builder().id(1).name("Milk").category(FOOD).rating(7)
                .createdDate(ZonedDateTime.of(2024, 1, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .modifiedDate(ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .build());

        List<Product> modified = warehouse.getModifiedProducts();
        assertEquals(1,modified.size());
        assertEquals("Milk",modified.getFirst().name());
        System.out.println("Found "+modified.size()+" Modified Products");
        modified.forEach(product -> System.out.println("ID: "+product.ID() + " Name: "+product.name()));
        System.out.println("Modified Date: "+modified.getFirst().modifiedDate());
    }

    @Test
    public void getModifiedProductsEmpty() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product.Builder().id(0).name("Bread").category(FOOD).rating(5)
                .createdDate(ZonedDateTime.of(2023, 2, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .modifiedDate(null)
                .build());

        List<Product> modified = warehouse.getModifiedProducts();
        assertThat(modified).isEmpty();
        System.out.println("Found "+modified.size()+" Modified Products");
        modified.forEach(product -> System.out.println("ID: "+product.ID() + " Name: "+product.name()));
    }


}
