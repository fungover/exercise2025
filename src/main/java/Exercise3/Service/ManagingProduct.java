package Exercise3.Service;

import Exercise3.Entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ManagingProduct implements Warehouse{

    List<Product> listOfProducts = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        listOfProducts.add(product);
    }
}
