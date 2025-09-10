package org.warehouse;

import org.warehouse.entities.Category;
import org.warehouse.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
  }
}
