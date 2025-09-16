package org.example.repository;

import org.example.entities.Product;

import java.util.List;
import java.util.Optional;

/**
 * ProductRepository interface that defined the contract for data access.
 * <p>
 * REPOSITORY PATTERN:
 * - Repository Pattern separates data access logic from business logic.
 * <p>
 * PROBLEMS WE HAD BEFORE:
 * - ProductService had both business logic and data storage (previous HashMap).
 * - Harder to change storage technology (from example HashMap to Database).
 * - This broke the Single Responsibility Principle
 * <p>
 * SOLUTION WITH REPOSITORY:
 * - Interface defines WHAT should be done (contract)
 * - Implementation defines HOW it's done (Concrete storage)
 * - Service uses the interface, not a concrete implementation.
 * - Easy-to-swap implementation (InMemory -> Database -> File)
 * - Easy to mock for tests.
 * <p>
 * WHY INTERFACE?
 * - Abstraction: Service only needs to know WHAT it can do, not how.
 * - Flexibility: Can have many implementations (InMemory, Database, File)
 * - Testability: Can easy mock interface for unit tests.
 * - Loose Coupling: Service is not bound to a specific implementation.
 */

public interface ProductRepository {

    void addProduct(Product product);

    Optional<Product> getProductById(String id);

    List<Product> getAllProducts();

    void updateProduct(Product product);
}
