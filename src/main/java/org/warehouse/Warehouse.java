package org.warehouse;

import org.warehouse.entities.Product;

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
}
