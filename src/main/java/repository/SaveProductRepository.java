package repository;

import entities.Product;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaveProductRepository implements ProductRepository {

    private final List<Product> products = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public void updateProduct(Product updatedProduct) {
        products.removeIf(p -> p.id().equals(updatedProduct.id()));
        products.add(updatedProduct);
    }
}
