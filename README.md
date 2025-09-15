# Warehouse Product Manager

## Overview
This project is a Java application for managing productService products.

At the moment it has a dog productService theme but can be easily extended to support other types of products.

The application targets Java 17+ (uses records and Stream.toList()) and JUnit 5 for unit testing.

## Structure
- `entities/Product.java`: Defines the product model using an immutable record.
- `entities/Category.java`: Enum representing product categories.
- `service/Warehouse.java`: Main service class containing business logic.
- `App.java`: Main application class.
- `test/WarehouseTest.java`: Unit tests for all public methods using JUnit 5.

## Product Entity
The `Product` class includes:
- `id`: Unique identifier
- `name`: Product name (must not be empty)
- `category`: Enum value
- `rating`: Integer between 0–10
- `createdDate`: Date of creation
- `modifiedDate`: Date of last modification

## Implemented Functionality
The following public methods are implemented in `Warehouse`:

- `addProduct(Product product)`: Adds a new product.
- `updateProduct(String id, String name, Category category, int rating)`: Updates an existing product.
- `getAllProducts()`: Returns all products.
- `getProductById(String id)`: Retrieves a product by its ID.
- `getProductsByCategorySorted(Category category)`: Returns products in a category, sorted alphabetically.
- `getProductsCreatedAfter(ZonedDateTime)`: Returns products created after the given date.
- `getModifiedProducts()`: Returns products where `createdDate` and `modifiedDate` differ.

All methods use Java Streams where appropriate.

## Testing
Each public method is tested with:
- At least one test for successful execution
- At least one test for expected failure (e.g., invalid input)

Tests are written using JUnit 5 and cover both typical and edge cases.

## Notes
- The application avoids exposing internal mutable structures.
- Creation and modification dates are handled using `ZonedDateTime`.
- The application uses UUIDs for product IDs.