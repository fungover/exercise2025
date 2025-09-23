package org.example.repository;

import org.example.Category;
import org.example.Product;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

	private final List<Product> products;

	public InMemoryProductRepository() {
		products = new ArrayList<Product>();
	}

	@Override
	public void addProduct(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null or empty");
		} else {
			products.add(product);
		}
	}

	@Override
	public List<Product> getAllProducts() {
		return Collections.unmodifiableList(products);
	}

	@Override
	public Product getProductById(String id) {
		return products.stream()
						.filter(product -> product.id().equals(id))
						.findFirst()
						.orElseThrow(() -> new NoSuchElementException("Product not found"));
	}

	@Override
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

}
