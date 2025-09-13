package org.warehouse;

import org.warehouse.entities.Category;
import org.warehouse.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Warehouse {
  private final String name;
  private final List<Product> products = new ArrayList<>();

  public Warehouse(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void addProduct(Product product) {
    boolean duplicate = products.stream().anyMatch(p -> p.id() == product.id());

    if (duplicate) {
      throw new IllegalArgumentException("Product with id " + product.id() + " already exists");
    }

    products.add(product);
  }

  public List<Product> getAllProducts() {
    if(products.isEmpty()) {
      throw new IllegalArgumentException("No products found");
    }
    return new ArrayList<>(products);
  }

  public void updateProduct(String id, String name, Category category, int rating) {
    for (int i = 0; i < products.size(); i++) {
      Product current = products.get(i);
      if (current.id().equals(id)) {
        Product updated = new Product(
                current.id(),
                name,
                category,
                rating,
                current.createdDate(),
                LocalDate.now()
        );
        products.set(i, updated);
        return;
      }
    }
    throw new IllegalArgumentException("Product with id " + id + " not found");
  }

  public Product getProductById(String id) {
    for (Product product : products) {
      if (product.id().equals(id)) {
        return product;
      }
    }
    throw new IllegalArgumentException("Product with id " + id + " not found");
  }

  public List<Product> getProductsByCategorySorted(Category category) {
    List<Product> result = products.stream()
            .filter(product -> product.category().equals(category))
            .sorted(Comparator.comparing(Product::name))
            .collect(Collectors.toList());

    if (result.isEmpty()) {
      throw new IllegalArgumentException("No products found in category " + category);
    }

    return result;
  }

  public List<Product> getProductsCreatedAfter(LocalDate date) {
    List<Product> result = products.stream()
            .filter(product -> product.createdDate().isAfter(date))
            .collect(Collectors.toList());

    if(result.isEmpty()) {
      throw new IllegalArgumentException("No products found on this date: " + date);
    }

    return result;
  }


  public List<Product> getModifiedProduct() {
    var result = products.stream()
            .filter(product -> !product.createdDate().equals(product.modifiedDate()))
            .collect(Collectors.toList());

    if(result.isEmpty()) {
      throw new IllegalArgumentException("No modified products was found");
    }

    return result;
  }
}