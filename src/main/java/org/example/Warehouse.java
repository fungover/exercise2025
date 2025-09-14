package org.example;

import java.time.LocalDateTime;
import java.util.*;

public class Warehouse {
	private final ArrayList<Product> products;

	public Warehouse() {
		products = new ArrayList<>();
	}

	public void addProduct(Product product) {
		if (product == null) {
		throw new IllegalArgumentException("Product cannot be null or empty");
		} else {
			products.add(product);
		}
	}

	public void updateProduct(String id, String name, Category category, int rating) {
		boolean updated = false;
		for (Product product : products) {
			if (product.id().equals(id)) {
				product.updateProduct(name, category, rating);
				updated = true;
				break;
			}
		}
		if (!updated) {
			throw new IllegalArgumentException("Product ID does not exist");
		}
	}

	public List<Product> getAllProducts() {
		return Collections.unmodifiableList(products);
	}

	public Product getProductById(String id) {
		return products.stream()
						.filter(product -> product.id().equals(id))
						.findFirst()
						.orElseThrow(() -> new NoSuchElementException("Product not found"));
	}

	public List<Product> getProductsByCategorySorted(Category category) {
		return products.stream()
						.sorted(Comparator.comparing(Product::name))
						.filter(product -> product.category().equals(category))
						.toList();
	}

	public List<Product> getProductsCreatedAfter(LocalDateTime now) {
		return products.stream().filter(product -> product.createdAt()
						.isAfter(now))
						.toList();
	}

	public List<Product> getModifiedProducts() {
		return products.stream()
						.filter(product -> !product.createdAt().equals(product.modifiedAt()))
						.toList();
	}
}
