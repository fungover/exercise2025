package exersice4.repository;

import exersice4.enteties.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products;

    public InMemoryProductRepository() {
        products = new ArrayList<>();
    }

    @Override
    public void addProduct(Product product) {
        if(product.getName() == null){
            System.out.println("Product could not be added");
            return;
        }
        products.add(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return products.stream()
                .filter(product -> product.getId().equals(id)).
                findFirst();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.copyOf(products);
    }

    @Override
    public void updateProduct(Product product) {
        Optional<Product> productToUpdate = products.stream().
                filter(p -> p.getId().equals(product.getId())).
                findFirst();

        if(productToUpdate.isPresent()){
            int index = products.indexOf(productToUpdate.get());
            products.set(index, product);


        }else{
            System.out.println("Product not found");
        }
    }
}
