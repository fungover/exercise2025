package app.service;

import app.entities.Category;
import app.entities.Product;
import app.repository.InMemoryProductRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static app.entities.Category.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService createService() {
        return new ProductService(new InMemoryProductRepository());
    }

    @Test
    public void addProduct() {
        ProductService productService = createService();
        productService.addProduct(new Product.Builder().name("Bread").category(FOOD).rating(5).build());
        List<Product> products = productService.products();


        assertThat(products)
                .hasSize(1)
                .contains(new Product.Builder().name("Bread").category(FOOD).rating(5).build());

    }

    @Test
    public void addProductNoName() {
        ProductService productService = createService();

        IllegalArgumentException fail = assertThrows(IllegalArgumentException.class,() ->
                productService.addProduct(new Product.Builder().category(FOOD).rating(5).build()));

        List<Product> products = productService.products();
        assertEquals("Product name cannot be null",fail.getMessage());
    }

    @Test
    public void addProductBlankName() {
        ProductService productService = createService();

        IllegalArgumentException fail = assertThrows(IllegalArgumentException.class,() ->
                productService.addProduct(new Product.Builder().name("").category(FOOD).rating(5).build()));

        List<Product> products = productService.products();
        assertEquals("Product name cannot be blank",fail.getMessage());
    }

    @Test
    public void updateProduct() {
        ProductService productService = createService();
        productService.addProduct(new Product.Builder().name("Bread").category(FOOD).rating(5).build());

        productService.updateProduct(0,"Milk",FOOD,7);

        assertEquals(1, productService.products().size());
        assertEquals("Milk", productService.products().getFirst().name());
    }

    @Test
    public void updateProductFail() {
        ProductService productService = createService();
        productService.addProduct(new Product.Builder().name("Bread").category(FOOD).rating(5).build());

        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(1,"Milk",FOOD,7));
    }

    @Test
    public void getAllProducts() {
        ProductService productService = createService();
        productService.addProduct(new Product.Builder().id(0).name("Bread").category(FOOD).rating(5).build());
        productService.addProduct(new Product.Builder().id(1).name("Milk").category(FOOD).rating(7).build());
        List<Product> products = productService.getAllProducts();

        assertEquals(2,products.size());
        assertEquals("Bread",products.get(0).name());
        assertEquals("Milk",products.get(1).name());

    }

    @Test
    public void getAllProductsEmpty() {
        ProductService productService = createService();
        List<Product> products = productService.getAllProducts();
        assertThat(products).isEmpty();
    }

    @Test
    void getProductByID() {
        ProductService productService = createService();
        productService.addProduct(new Product.Builder().id(0).name("Bread").category(FOOD).rating(5).build());
        productService.addProduct(new Product.Builder().id(1).name("Milk").category(FOOD).rating(7).build());
        productService.addProduct(new Product.Builder().id(2).name("Coffee").category(FOOD).rating(3).build());

        Product product = productService.getProductByID(2);
        assertEquals(2,product.ID());
        assertEquals("Coffee",product.name());
        assertEquals(FOOD,product.category());
        assertEquals(3,product.rating());

    }

    @Test
    void getProductByIDFail() {
        ProductService productService = createService();
        productService.addProduct(new Product.Builder().id(0).name("Bread").category(FOOD).rating(5).build());
        productService.addProduct(new Product.Builder().id(1).name("Milk").category(FOOD).rating(7).build());
        productService.addProduct(new Product.Builder().id(2).name("Coffee").category(FOOD).rating(3).build());
        Product product  = productService.getProductByID(10);
        assertNull(product);

    }

    @Test
    void getProductByCategory() {
        ProductService productService = createService();
        productService.addProduct(new Product.Builder().id(0).name("Amiga 500").category(ELECTRONICS).rating(5).build());
        productService.addProduct(new Product.Builder().id(1).name("Playstation").category(ELECTRONICS).rating(7).build());
        productService.addProduct(new Product.Builder().id(2).name("Nintendo Wii").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(3).name("Nintendo 64").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(4).name("Xbox 360").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(5).name("Playstation 2").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(6).name("Xbox One").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(7).name("Playstation 4").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(8).name("Playstation 5").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(9).name("Xbox").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(10).name("Playstation 3").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(11).name("Phone").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(12).name("Tablet").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(13).name("Xbox Series X").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(14).name("Laptop").category(ELECTRONICS).rating(5).build());
        productService.addProduct(new Product.Builder().id(15).name("PC").category(ELECTRONICS).rating(5).build());
        productService.addProduct(new Product.Builder().id(16).name("Nintendo Switch").category(ELECTRONICS).rating(10).build());
        productService.addProduct(new Product.Builder().id(17).name("Dreamcast").category(ELECTRONICS).rating(10).build());


        List<Product> updatedlist = productService.getProductsByCategory(Category.ELECTRONICS);
        assertThat(updatedlist).hasSize(18)
                .allMatch(product -> product.category() == ELECTRONICS);
    }

    @Test
    public void getProductsByCategoryEmpty() {
        ProductService productService = createService();
        List<Product> updatedlist = productService.getProductsByCategory(Category.ELECTRONICS);
        assertThat(updatedlist).isEmpty();
    }

    @Test
    void getProductsCreatedAfter() {
        ProductService productService = createService();
        productService.addProduct(new Product(0,"Bread",FOOD,5,
                ZonedDateTime.of(2025, 2, 1, 12, 0, 0, 0,
                        ZoneId.of("Europe/Stockholm")), null));

        productService.addProduct(new Product(1,"Milk",FOOD,7,
                ZonedDateTime.of(2024, 1, 1, 12, 0, 0, 0,
                        ZoneId.of("Europe/Stockholm")), null));

        LocalDate cutoff = LocalDate.of(2025, 1, 1);
        List<Product> updatedlist = productService.getProductsCreatedAfter(cutoff);
        assertEquals(1,updatedlist.size());
        assertEquals("Bread",updatedlist.getFirst().name());

    }

    @Test
    public void getProductsCreatedAfterEmpty() {
        ProductService productService = createService();
        productService.addProduct(new Product(0,"Bread",FOOD,5,
                ZonedDateTime.of(2023, 2, 1, 12, 0, 0, 0,
                        ZoneId.of("Europe/Stockholm")), null));

        LocalDate cutoff = LocalDate.of(2025, 1, 1);
        List<Product> updatedlist = productService.getProductsCreatedAfter(cutoff);
        assertThat(updatedlist).isEmpty();
    }

    @Test
    void getModifiedProducts() {
        ProductService productService = createService();
        productService.addProduct(new Product.Builder().id(0).name("Bread").category(FOOD).rating(5)
                .createdDate(ZonedDateTime.of(2025, 2, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .modifiedDate(ZonedDateTime.of(2025, 2, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .build());

        productService.addProduct(new Product.Builder().id(1).name("Milk").category(FOOD).rating(7)
                .createdDate(ZonedDateTime.of(2024, 1, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .modifiedDate(ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .build());

        List<Product> modified = productService.getModifiedProducts();
        assertEquals(1,modified.size());
        assertEquals("Milk",modified.getFirst().name());
    }

    @Test
    public void getModifiedProductsEmpty() {
        ProductService productService = createService();
        productService.addProduct(new Product.Builder().id(0).name("Bread").category(FOOD).rating(5)
                .createdDate(ZonedDateTime.of(2023, 2, 1, 12, 0, 0, 0, ZoneId.of("Europe/Stockholm")))
                .modifiedDate(null)
                .build());

        List<Product> modified = productService.getModifiedProducts();
        assertThat(modified).isEmpty();
    }


}
