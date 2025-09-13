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
    boolean duplicate = products.stream().anyMatch(p -> p.getId() == product.getId());

    if (duplicate) {
      throw new IllegalArgumentException("Product with id " + product.getId() + " already exists");
    }

    products.add(product);
  }

  public List<Product> getAllProducts() {
    return new ArrayList<>(products);
  }

  public void updateProduct(String id, String name, Category category, int rating) {
    for (int i = 0; i < products.size(); i++) {
      Product current = products.get(i);
      if (current.getId().equals(id)) {
        Product updated = new Product(
                current.getId(),
                name,
                category,
                rating,
                current.getCreatedDate(),
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
      if (product.getId().equals(id)) {
        return product;
      }
    }
    throw new IllegalArgumentException("Product with id " + id + " not found");
  }

  public List<Product> getProductsByCategorySorted(Category category) {
    return products.stream()
            .filter(product -> product.getCategory().equals(category))
            .sorted(Comparator.comparing(Product::getName))
            .collect(Collectors.toList());
  }

  public List<Product> getProductsCreatedAfter(LocalDate date) {
    return products.stream().filter(product -> product.getCreatedDate().isAfter(date)).collect(Collectors.toList());
  }


  public List<Product> getModifiedProduct() {
    return products.stream().filter(product -> product.getCreatedDate().equals(product.getModifiedDate())).collect(Collectors.toList());
  }
}
