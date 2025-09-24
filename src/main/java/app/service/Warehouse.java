package app.service;

import app.entities.Category;
import app.entities.Product;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.name() == null) {
            throw new IllegalArgumentException("Product name cannot be null");
        } else if (product.name().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        products.add(product);
    }

    public List<Product> products() {
        return Collections.unmodifiableList(products);
    }

    public void updateProduct(int id, String name, Category category, int rating) {
       Product productID = products.stream()
               .filter(product -> product.ID() == id)
               .findFirst()
               .orElseThrow( () -> new IllegalArgumentException("Product with id " + id + " does not exist"));

       Product updatedProduct = new Product(
               productID.ID(),
               name,category,
               rating,
               productID.createdDate(),
               ZonedDateTime.now(ZoneId.of("Europe/Stockholm"))
       );
       products.set(id,updatedProduct);
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product getProductByID(int ID) {
        Product searchedProduct = products.stream()
                .filter(product -> product.ID() == ID)
                .findFirst().orElse(null);

        if (searchedProduct == null) {
            System.out.println("Product with id " + ID + " does not exist");
        }
    return searchedProduct;
    }


    public List<Product> getProductsByCategory(Category category) {
        return products.stream()
                .filter(p->p.category()==category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return products.stream()
                .filter(product -> product.createdDate() != null &&
                        product.createdDate().toLocalDate().isAfter(date))
                .sorted(Comparator.comparing(Product::createdDate))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(product -> !product.createdDate().equals(product.modifiedDate()))
                .collect(Collectors.toList());
    }
}
