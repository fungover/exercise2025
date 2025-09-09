package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagingProductTest {

    ManagingProduct managingProduct = new ManagingProduct();

    @Test
    public void productIsAddedToListWhenCreated() {

        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 9));
        //, LocalDate.now(), LocalDate.now()
        assertEquals(1,managingProduct.getAllProducts().size());
        assertEquals("Knitted shirt",managingProduct.getAllProducts().getFirst().name());
        assertEquals(Category.SHIRT, managingProduct.getAllProducts().getFirst().category());
    }

    @Test
    public void productIsAddedToListWhenUpdated() {}

    @Test
    public void allProductsAreAddedToList(){
        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 9));
        managingProduct.addProduct(new Product("Tina", "Flared jeans", Category.JEANS, 9));
        managingProduct.addProduct(new Product("Joell", "Dressed pants", Category.PANTS, 9));

        assertEquals(3,managingProduct.getAllProducts().size());
    }

    @Test
    public void returnsCorrectIdWhenSearchingProduct(){
        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 9));
        managingProduct.addProduct(new Product("Tina", "Flared jeans", Category.JEANS, 9));

        assertEquals("Bella", managingProduct.getProductById("Bella").id());

    }

    @Test
    public void returnsListSortedByCategoryInAlphabeticOrder(){
        managingProduct.addProduct(new Product("Tina", "Knitted dress", Category.DRESS, 9));
        managingProduct.addProduct(new Product("Joell", "Dressed dress", Category.DRESS, 6));
        managingProduct.addProduct(new Product("Bella", "Evening dress", Category.DRESS, 5));
        managingProduct.addProduct(new Product("Anna", "Cocktail dress", Category.DRESS, 10));
        managingProduct.addProduct(new Product("Milo", "Flared jeans", Category.JEANS, 10));
        managingProduct.addProduct(new Product("Leon", "Dressed pants", Category.PANTS, 10));

        List <Product> sortedProducts = managingProduct.getProductsByCategorySorted(Category.DRESS);

        assertEquals(4,sortedProducts.size());
        assertEquals("Cocktail dress", sortedProducts.getFirst().name());
        assertEquals("Dressed dress",sortedProducts.get(1).name());
        assertEquals("Evening dress",sortedProducts.get(2).name());
        assertEquals("Knitted dress",sortedProducts.get(3).name());
    }



}