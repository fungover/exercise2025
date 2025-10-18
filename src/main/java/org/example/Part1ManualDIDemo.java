package org.example;

import org.example.entities.User;
import org.example.repository.UserRepository;
import org.example.repository.InMemoryUserRepository;
import org.example.repository.DatabaseUserRepository;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;

/**
 * Part 1: Manual Constructor Injection Demo
 *
 * This class demonstrates manual dependency injection using constructor injection.
 * We manually instantiate all dependencies and pass them to constructors.
 *
 * Key principles:
 * 1. Each class declares its dependencies in the constructor
 * 2. Dependencies are stored as final fields (immutable)
 * 3. In the Main class (here), we manually create instances and wire them together
 * 4. No getters/setters for dependencies, no static factories for DI purposes
 */
public class Part1ManualDIDemo {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Part 1: Manual Constructor Injection Demo");
        System.out.println("=".repeat(60));

        // ========== Scenario 1: Using InMemoryUserRepository ==========
        System.out.println("\n--- Scenario 1: In-Memory Repository ---");
        demonstrateWithInMemoryRepository();

        System.out.println("\n");

        // ========== Scenario 2: Using DatabaseUserRepository ==========
        System.out.println("--- Scenario 2: Database Repository ---");
        demonstrateWithDatabaseRepository();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("Manual DI Demo Complete!");
        System.out.println("=".repeat(60));
    }

    /**
     * Demonstrates dependency injection with in-memory repository.
     * Shows how dependencies are manually wired together.
     */
    private static void demonstrateWithInMemoryRepository() {
        // Step 1: Manually instantiate the repository (leaf dependency)
        UserRepository userRepository = new InMemoryUserRepository();
        System.out.println("✓ Created InMemoryUserRepository");

        // Step 2: Manually instantiate the service, injecting the repository
        UserService userService = new UserServiceImpl(userRepository);
        System.out.println("✓ Created UserServiceImpl with repository dependency");

        // Step 3: Use the service
        System.out.println("\nUsing the service:");
        User user1 = new User("1", "Alice Johnson", "alice@example.com");
        User user2 = new User("2", "Bob Smith", "bob@example.com");

        userService.registerUser(user1);
        userService.registerUser(user2);

        System.out.println("\nAll users: " + userService.getAllUsers());

        userService.updateUserEmail("1", "alice.johnson@newdomain.com");
        System.out.println("User after email update: " + userService.findUserById("1"));
    }

    /**
     * Demonstrates dependency injection with database repository.
     * Shows how different implementations can be swapped by changing only the repository creation.
     */
    private static void demonstrateWithDatabaseRepository() {
        // Step 1: Manually instantiate a different repository implementation
        UserRepository databaseRepository = new DatabaseUserRepository("production_db");
        System.out.println("✓ Created DatabaseUserRepository");

        // Step 2: Same service code, but now with database repository
        // This demonstrates the power of DI - the service doesn't know or care which repository it gets
        UserService userService = new UserServiceImpl(databaseRepository);
        System.out.println("✓ Created UserServiceImpl with database repository dependency");

        // Step 3: Use the service - same interface, different behavior
        System.out.println("\nUsing the service with database repository:");
        User user3 = new User("3", "Charlie Brown", "charlie@example.com");
        User user4 = new User("4", "Diana Prince", "diana@example.com");

        userService.registerUser(user3);
        userService.registerUser(user4);

        System.out.println("\nAll users: " + userService.getAllUsers());

        userService.updateUserEmail("3", "c.brown@company.com");
        System.out.println("User after email update: " + userService.findUserById("3"));

        userService.removeUser("4");
        System.out.println("Removed user '4'. Remaining users: " + userService.getAllUsers());
    }
}
