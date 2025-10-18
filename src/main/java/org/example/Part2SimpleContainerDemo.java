package org.example;

import org.example.container.SimpleContainer;
import org.example.entities.User;
import org.example.repository.UserRepository;
import org.example.repository.InMemoryUserRepository;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;


public class Part2SimpleContainerDemo {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Part 2: Simple DI Container Demo");
        System.out.println("=".repeat(60));

        // ---Create the container---
        SimpleContainer container = new SimpleContainer();

        // ---- Register interface-to-implementation mappings ----
        container.register(UserRepository.class, InMemoryUserRepository.class);
        container.register(UserService.class, UserServiceImpl.class);

        System.out.println("\n✓ Created SimpleContainer");
        System.out.println("✓ Registered implementations:");
        System.out.println("  - UserRepository -> InMemoryUserRepository");
        System.out.println("  - UserService -> UserServiceImpl");
        System.out.println("  Initial cached instances: " + container.getCachedInstanceCount());

        // --- Scenario 1: Automatic dependency resolution ---
        System.out.println("\n--- Scenario 1: Container Resolves Dependencies Automatically ---");
        demonstrateAutomaticResolution(container);

        System.out.println("\n--- Scenario 2: Container Caches Singletons ---");
        demonstrateSingletonCaching(container);

        System.out.println("\n--- Scenario 3: New Container Instance ---");
        demonstrateMultipleContainers();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("Container Demo Complete!");
        System.out.println("=".repeat(60));
    }

    // --- Shows how the container automatically resolves the dependency graph ---
    private static void demonstrateAutomaticResolution(SimpleContainer container) {
        System.out.println("\nAsking container for: UserService");
        System.out.println("Container will automatically:");
        System.out.println("  1. Inspect UserServiceImpl constructor");
        System.out.println("  2. See it needs UserRepository");
        System.out.println("  3. Recursively resolve UserRepository");
        System.out.println("  4. Inject UserRepository into UserService");

        // ---handles all dependency resolution---
        UserService userService = container.get(UserService.class);

        System.out.println("\n✓ Container returned fully wired UserService!");
        System.out.println("  Cached instances now: " + container.getCachedInstanceCount());

        // --Verify--
        System.out.println("\nUsing the service:");
        User user = new User("101", "Eve Wilson", "eve@example.com");
        userService.registerUser(user);

        System.out.println("Retrieved user: " + userService.findUserById("101"));
    }

    // --container caches instances--
    private static void demonstrateSingletonCaching(SimpleContainer container) {
        System.out.println("\nRequesting UserService again from the same container...");

        container.get(UserService.class);
        System.out.println("✓ Got UserService from cache (same instance)");
        System.out.println("  Cached instances: " + container.getCachedInstanceCount());

        UserRepository repo = container.get(UserRepository.class);
        System.out.println("✓ Got UserRepository from cache");
        System.out.println("  Cached instances: " + container.getCachedInstanceCount());

        System.out.println("\nAll users in cached repository: " + repo.getAllUsers());
    }


    // --new container instances have their own cache--
    private static void demonstrateMultipleContainers() {
        System.out.println("\nCreating a new container...");
        SimpleContainer newContainer = new SimpleContainer();

        // --Register in the new container--
        newContainer.register(UserRepository.class, InMemoryUserRepository.class);
        newContainer.register(UserService.class, UserServiceImpl.class);

        // -- implementation mapping --
        UserService userService = newContainer.get(UserService.class);
        System.out.println("✓ New container created and resolved UserService");
        System.out.println("  This container has: " + newContainer.getCachedInstanceCount() + " cached instances");

        // --Verify its independent
        User user = new User("202", "Frank Miller", "frank@example.com");
        userService.registerUser(user);
        System.out.println("  New container's repository contains: " + userService.getAllUsers());
    }
}
