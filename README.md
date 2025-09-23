# Warehouse Product Manager

## Overview
This project is a simple **Product Management application** written in Java.  
It demonstrates modern practices such as immutability, validation, and the use of **design patterns** (Builder Pattern + Repository Pattern).

The core service is `ProductService`, which manages `Product` entities through a `ProductRepository`.  
For this implementation, an in-memory repository (`InMemoryProductRepository`) is provided.

---

## Features
- Add products with validation (id, name, category, rating).
- Update products (immutable replace, keep createdDate, set modifiedDate).
- Retrieve all products (defensive copy).
- Retrieve a product by id.
- Filter products by category, sorted A–Z (case-insensitive).
- Filter products created strictly after a given date.
- Retrieve modified products (where modifiedDate != createdDate).
- Full unit test coverage using JUnit 5.

---

## Design Patterns

### Builder Pattern
`Product` uses a nested `Builder` to simplify creation and centralize validation.

Example:
```java
Product product = new Product.Builder()
        .id("1")
        .name("Laptop")
        .category(Category.ELECTRONICS)
        .rating(9)
        .build();
```

### Repository Pattern
Data access is separated from business logic.

- `ProductRepository`: defines CRUD contract.
- `InMemoryProductRepository`: manages products in memory using a `HashMap`.
- `ProductService`: business logic layer that depends on a `ProductRepository`.

Example setup:
```java
import java.time.Clock;
import com.jan_elia.warehouse.repository.*;

ProductRepository repo = new InMemoryProductRepository();
ProductService service = new ProductService(Clock.systemDefaultZone(), repo);

Product product = new Product.Builder()
        .id("1")
        .name("Phone")
        .category(Category.ELECTRONICS)
        .rating(8)
        .build();

service.addProduct(product);
```

---

## Requirements
- Java 24+
- Maven 3.9+
- IntelliJ IDEA

---

## How to Run

### In IntelliJ
1. Open the project.
2. Run tests with the **JUnit 5** runner.

### From CLI
```bash
mvn clean test
```

---

## Tests
- Framework: JUnit 5
- All public methods in `ProductService` are covered with **success and failure cases**.
- Repository and builder validation tested.

Run tests:
```bash
mvn test
```

---