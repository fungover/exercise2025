package org.example;


import java.time.LocalDateTime;

public class Product {
	private final String id;
	private String name;
	private Category category;
	private int rating;
	private final LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	private Product(String id, String name, Category category, int rating) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("id cannot be null or empty");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name cannot be null or empty");
		}
		if (rating < 0 || rating > 10) {
			throw new IllegalArgumentException("rating should be between 0 and 10");
		}

		this.id = id;
		this.name = name;
		this.category = category;
		this.rating = rating;
		this.createdAt = LocalDateTime.now();
		this.modifiedAt = this.createdAt;
	}

	public void updateProduct(String name, Category category, int rating) {
		if (rating < 0 || rating > 10) {
			throw new IllegalArgumentException("rating should be between 0 and 10");
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name cannot be null or empty");
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

	private void setModifiedAt() {
		this.modifiedAt = LocalDateTime.now();
	}

	public static class ProductBuilder {
		private String id;
		private String name;
		private Category category;
		private int rating;

		public ProductBuilder setId(String id) {
			this.id = id;
			return this;
		}

		public ProductBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ProductBuilder setCategory(Category category) {
			this.category = category;
			return this;
		}

		public ProductBuilder setRating(int rating) {
			this.rating = rating;
			return this;
		}

		public Product build() {
			return new Product(id, name, category, rating);
		}
	}
}

