# Product Management System

A Java application demonstrating the implementation of common design patterns: **Builder**, **Repository**, and **Decorator**.  
The system manages a collection of pet products with features for creation, storage, retrieval, and dynamic pricing through decorators.

## Features
- **Product Management**: Create, read, update, and manage pet products
- **Category-based Organization**: Organize products by categories (`FOOD`, `TOYS`, `GROOMING`, `TRAINING`, `BEDDING`)
- **Flexible Product Creation**: Build products easily with the **Builder Pattern**
- **Data Access Abstraction**: **Repository Pattern** separates business logic from data storage
- **Dynamic Pricing**: **Decorator Pattern** allows applying discounts without changing the original product

## Design Patterns Implemented

### 1. Builder Pattern (Creational)
`Product.Builder` enables flexible and readable product creation with validation.

```java
Product product = new Product.Builder()
    .id(UUID.randomUUID().toString())
    .name("Chew Bone")
    .category(Category.FOOD)
    .price(BigDecimal.valueOf(45))
    .rating(8)
    .build();
```

### 2. Repository Pattern (Structural)

Separates data storage logic from business logic via the `ProductRepository` interface.

**Components:**
- `ProductRepository`: Defines data access contract
- `InMemoryProductRepository`: In-memory implementation
- `ProductService`: Business logic with dependency injection


### 3. Decorator Pattern (Structural)

Adds functionality (like discounts) without modifying the original object.

**Components:**

- `Sellable`: Interface for sellable items

- `ProductDecorator`: Abstract base class

- `DiscountDecorator`: Concrete decorator for discounts