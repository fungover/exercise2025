package exercise4;


import exercise4.entities.Category;
import exercise4.entities.Product;
import exercise4.repository.InMemoryProductRepository;
import exercise4.repository.ProductRepository;
import exercise4.service.ProductServices;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServicesTest {
    @Test
    void testAddProductSuccess() {
        Product product = new Product.Builder().
                setName("Rose soap")
                .build();
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        productServices.addProduct(product);
        assertEquals(1, productServices.getAllProducts().size());
        assertEquals(product, productServices.getAllProducts().getFirst());
    }

    @Test()
    void testAddProductFail() {
        Product product = new Product.Builder()
                .setName(null)
                .build();
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        productServices.addProduct(product);
        assertEquals(0, productServices.getAllProducts().size());
    }

    @Test
    void testUpdateProductSuccess() {
        Product product = new Product.Builder()
                .setName("Rose soap")
                .build();
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        productServices.addProduct(product);
        Product updatedProduct = new Product.Builder()
                .setName("Pink soap")
                .setCategory(Category.Soap)
                .setRating(8)
                .setCreatedDate(product.getCreatedDate())
                .setId(product.getId())
                .build();
        productServices.updateProduct(updatedProduct);
        assertEquals("Pink soap", productServices.getAllProducts().getFirst().getName());
        assertEquals(Category.Soap, productServices.getAllProducts().getFirst().getCategory());
        assertEquals(8, productServices.getAllProducts().getFirst().getRating());
        assertEquals(LocalDateTime.now().getYear(), productServices.getAllProducts().getFirst().getModifiedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), productServices.getAllProducts().getFirst().getModifiedDate().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), productServices.getAllProducts().getFirst().getModifiedDate().getDayOfMonth());
    }

    @Test
    void testUpdateProductFail() {
        Product product = new Product.Builder()
                .setName("Rose soap")
                .build();
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        productServices.addProduct(product);
        Product updatedProduct = new Product.Builder()
                .setName("Pink soap")
                .setCategory(Category.Soap)
                .setRating(8)
                .setCreatedDate(product.getCreatedDate())
                .setModifiedDate(LocalDateTime.now())
                .setId("tre")
                .build();
        productServices.updateProduct(updatedProduct);
        assertEquals("Rose soap", productServices.getAllProducts().getFirst().getName());
        assertEquals(product.getCreatedDate(), product.getModifiedDate());
    }

    @Test
    void testGetAllProductsSuccess() {
        Product product = new Product.Builder()
                .setName("Rose soap")
                .build();
        Product product2 = new Product.Builder()
                .setName("Lavender soap")
                .build();
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        productServices.addProduct(product);
        productServices.addProduct(product2);
        List<Product> allProducts = productServices.getAllProducts();
        assertEquals(2, allProducts.size());
    }

    @Test
    void testGetAllProductsFail() {
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        List<Product> allProducts = productServices.getAllProducts();
        assertEquals(0, allProducts.size());
    }

    @Test
    void testGetProductByIdSuccess() {
        Product product = new Product.Builder()
                .setName("Rose soap")
                .build();
        String id = product.getId();
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        productServices.addProduct(product);
        Optional<Product> product2 = productServices.getProductById(id);
        Product productById = null;
        if(product2.isPresent()) {
            productById = product2.get();
        }
        assertEquals("Rose soap", productById.getName());

    }
    @Test
    void testGetProductByIdFail() {
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        Optional<Product> product = productServices.getProductById("tre");
        assertTrue(product.isEmpty());
    }
    @Test
    void testGetProductsByCategorySortedSuccess(){
        Product product = new Product.Builder()
                .setName("Rose soap")
                .setCategory(Category.Soap)
                .setRating(8)
                .build();
        Product product2 = new Product.Builder()
                .setName("Lavender soap")
                .setCategory(Category.Soap)
                .setRating(5)
                .build();
        Product product3 = new Product.Builder()
                .setName("Vanilla shampoo")
                .setCategory(Category.Shampoo)
                .setRating(4)
                .build();
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        productServices.addProduct(product);
        productServices.addProduct(product2);
        productServices.addProduct(product3);
        List<Product> productsInCategory = productServices.getProductsByCategorySorted(Category.Soap);
        assertEquals(2, productsInCategory.size());
        assertEquals("Lavender soap", productsInCategory.getFirst().getName());
    }
    @Test
    void testGetProductsByCategorySortFail(){
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        Product product = new Product.Builder()
                .setName("Vanilla shampoo")
                .setCategory(Category.Shampoo)
                .setRating(4)
                .build();
        productServices.addProduct(product);
        List<Product> productsInCategory = productServices.getProductsByCategorySorted(Category.Soap);
        assertEquals(0, productsInCategory.size());
    }
    @Test
    void testGetProductsCreatedAfterSuccess(){
        Product product = new Product.Builder()
                .setName("Rose soap")
                .build();
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        productServices.addProduct(product);
        LocalDateTime date = LocalDateTime.of(2025,6,10, 12, 0, 0);
        List<Product> productsCreatedAfter = productServices.getProductsCreatedAfter(date);
        assertEquals(1, productsCreatedAfter.size());
    }
    @Test
    void testGetProductsCreatedAfterFail(){
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        LocalDateTime date = LocalDateTime.of(2025,6,10, 12, 0, 0);
        List<Product> productsCreatedAfter = productServices.getProductsCreatedAfter(date);
        assertEquals(0, productsCreatedAfter.size());
    }
    @Test
    void testGetModifiedProductsSuccess(){
        Product product = new Product.Builder()
                .setName("Rose soap")
                .setCategory(Category.Shampoo)
                .setRating(7)
                .setCreatedDate(LocalDateTime.of(2025,6,10, 12, 0, 0))
                .build();
        Product product2 = new Product.Builder()
                .setName("Lavender soap")
                .build();
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        productServices.addProduct(product);
        productServices.addProduct(product2);
        Product updatedProduct = new Product.Builder()
                .setName("Pink soap")
                .setCategory(Category.Soap)
                .setRating(8)
                .setCreatedDate(product.getCreatedDate())
                .setId(product.getId())
                .build();
        productServices.updateProduct(updatedProduct);
        List<Product> modifiedProducts = productServices.getModifiedProducts();
        assertEquals(1, modifiedProducts.size());
    }
    @Test
    void testGetModifiedProductsFail(){
        ProductRepository pr = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(pr);
        Product product = new Product.Builder()
                .setName("Rose soap")
                .build();
        productServices.addProduct(product);
        List<Product> modifiedProducts = productServices.getModifiedProducts();
        assertEquals(0, modifiedProducts.size());
    }
}
