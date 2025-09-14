package org.example;


import java.time.LocalDateTime;

public class Product {
	private final String id;
	private String name;
	private Category category;
	private int rating;
	private final LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public Product(String id, String name, Category category, int rating) {
		if (rating > 10 || rating < 0) {
			throw new IllegalArgumentException("Invalid rating");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be empty");
		}
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("ID cannot be empty");
		}

		this.id = id;
		this.name = name;
		this.category = category;
		this.rating = rating;
		this.createdAt = LocalDateTime.now();
		this.modifiedAt = this.createdAt;
	}

	public void updateProduct(String name, Category category, int rating) {
		if (rating > 10 || rating < 0 ) {
			throw new IllegalArgumentException("Invalid rating");
		}
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Product name cannot be empty");
		}
		this.name = name;
		this.category = category;
		this.rating = rating;
		setModifiedAt();
	}

	public String name() {
		return name;
	}

	public String id() {
		return id;
	}

	public Category category() {
		return category;
	}

	public int rating() {
		return rating;
	}

	public LocalDateTime createdAt() {
		return createdAt;
	}

	public LocalDateTime modifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt() {
		this.modifiedAt = LocalDateTime.now();
	}

}

