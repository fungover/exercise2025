package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ManagingProductTest {

    @Test
    public void productIsAddedToWarehouse() {
        ManagingProduct managingProduct = new ManagingProduct();

        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 9, LocalDate.now(), LocalDate.now()));

    }

}