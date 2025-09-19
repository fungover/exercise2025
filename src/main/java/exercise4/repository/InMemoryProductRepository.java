package exercise4.repository;

import exercise4.entities.Product;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> listOfProducts = new ArrayList<>();


    @Override
    public void addProduct(Product product) {

        Objects.requireNonNull(product, "Product cannot be null");

        boolean exists = listOfProducts.stream()
                .anyMatch(item -> item.getId().equals(product.getId()));

        if(exists) {
            throw new IllegalArgumentException("Product already exists");
        }

        listOfProducts.add(product);
    }

    @Override
    public void updateProduct(Product product) {

        if(listOfProducts.contains(product)){
            throw new IllegalArgumentException("Product already exists");
        }

        listOfProducts.add(product);

    }

    public List<Product> getAllProducts() {

        if (listOfProducts.isEmpty()) {
            throw new IllegalArgumentException("Product list is empty");
        }

        return Collections.unmodifiableList(listOfProducts);
    }

    @Override
    public Product getProductById(String id) {

        Objects.requireNonNull(id, "Product id cannot be null");

        Product productById = listOfProducts.stream()
                .filter(item -> item.getId().equals(id.trim()))
                .max(Comparator.comparing(Product::getModifiedDate))
                .orElse(null);

        if(productById == null){
            throw new IllegalArgumentException("Product doesn't exist");
        }

        return productById;
    }

}
