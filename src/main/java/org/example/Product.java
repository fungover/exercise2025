package org.example;


import java.time.LocalDateTime;

public class Product {
	private String id;
	private String name;
	private Category category;
	private int rating;
	private final LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public Product(String id, String name, Category category, int rating) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.rating = rating;
		this.createdAt = LocalDateTime.now();
		this.modifiedAt = LocalDateTime.now();
	}

	public void updateProduct(String id, String name, Category category, int rating) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.category = category;
		setModifiedAt();
	}

	public String name() {
		return name;
	}

	public String ID() {
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

