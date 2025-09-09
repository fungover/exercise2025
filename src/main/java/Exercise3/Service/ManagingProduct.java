package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManagingProduct implements Warehouse {

    private final List<Product> listOfProducts = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        listOfProducts.add(product);
    }

    @Override
    public void updateProduct(String id, String name, Category category, int rating) {
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(listOfProducts);
    }

    @Override
    public Product getProductById(String id) {
        return listOfProducts.stream()
                .filter(item -> item.id().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Product> getProductsByCategorySorted(Category category) {

        return listOfProducts.stream()
                .filter(item -> item.category().equals(category))
                .sorted(Comparator.comparing(Product::name)).toList();
    }


}
