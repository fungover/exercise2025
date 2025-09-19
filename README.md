# Warehouse Product Manager

## Overview
A simple Java application for managing products.  
The project demonstrates:
- Immutable entities (`Product`)
- A service layer (`Warehouse`) with validation and defensive programming
- Full unit test coverage with JUnit 5

---

## How to Run

### IntelliJ IDEA
1. Open the project in IntelliJ
2. Right-click on `WarehouseTest.java`
3. Choose **Run 'WarehouseTest'**
4. Or use the Maven tool window → Lifecycle → **test**

### Command Line
Open a terminal in the project root and run:

```bash
mvn test
```

---

## Design Decisions
- **Warehouse** is the public API (service layer)
- **Product** is immutable (all fields are final, getters only)
- **Category** is a simple enum used by Product
- Internal storage: `Map<String, Product>`
- No internal collections exposed – all lists are defensive and unmodifiable

---

## Validation Rules
- **id**: non-null, non-blank, must be unique
- **name**: non-null, non-blank
- **category**: non-null
- **rating**: integer between 0–10
- **dates**:
    - `createdDate` set at creation
    - `modifiedDate` updated on change
- **time source**: `Warehouse` uses injected `Clock` for deterministic behavior
- **errors**:
    - `IllegalArgumentException` for invalid input
    - `NoSuchElementException` for missing id

---

## Public API
- `addProduct(Product product)`
- `updateProduct(String id, String name, Category category, int rating)`
- `getAllProducts(): List<Product>`
- `getProductById(String id): Product`
- `getProductsByCategorySorted(Category category): List<Product>`
- `getProductsCreatedAfter(LocalDate date): List<Product>`
- `getModifiedProducts(): List<Product>`

---

## Testing
- JUnit 5
- Each public method has tests for success and failure cases
- Fixed `Clock` is used to control dates and make tests deterministic

---
