package service;

import entities.Product;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository{

    private final List<Product> products = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return products.stream()
                .filter(p -> String.valueOf(p.id()).equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (Objects.equals(products.get(i).id(), product.id())) {
                products.set(i, product);
                return;
            }
        }
        throw new NoSuchElementException("Product not found");
    }
}
