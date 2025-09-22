package exercise4.repository;

import exercise4.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products;

    public InMemoryProductRepository() {
        products = new ArrayList<>();
    }

    @Override
    public void addProduct(Product product) {
        if(product == null
                || product.getId() == null || product.getId().isEmpty()
                || product.getName() == null || product.getName().isBlank()){
            System.out.println("Product could not be added");
            return;
        }
        products.add(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        if(id == null || id.isBlank()){
            return Optional.empty();
        }
        return products.stream()
                .filter(product -> id.equals(product.getId())).
                findFirst();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.copyOf(products);
    }

    @Override
    public void updateProduct(Product product) {
        if(product == null || product.getId() == null || product.getId().isBlank()){
            System.out.println("Invalid product");
            return;
        }
        Optional<Product> productToUpdate = products.stream().
                filter(p -> Objects.equals(p.getId(), product.getId())).
                findFirst();

        if(productToUpdate.isPresent()){
            int index = products.indexOf(productToUpdate.get());
            products.set(index, product);


        }else{
            System.out.println("Product not found");
        }
    }
}
