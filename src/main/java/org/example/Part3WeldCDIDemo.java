package org.example;

import org.example.entities.User;
import org.example.service.UserService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;


public class Part3WeldCDIDemo {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Part 3: Weld (CDI) Demo");
        System.out.println("=".repeat(60));

        // --start Weld container--
        System.out.println("\n✓ Starting Weld container...");
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            System.out.println("✓ Weld container initialized");
            System.out.println("  Weld scanned classpath for @ApplicationScoped beans");

            // --- Scenario 1: Get bean from Weld ---
            System.out.println("\n--- Scenario 1: Get UserService from Weld ---");
            demonstrateWeldResolution(container);

            System.out.println("\n--- Scenario 2: Weld Returns Same Instance (Singleton) ---");
            demonstrateWeldSingleton(container);

            System.out.println("\n--- Scenario 3: Compare with Part 1 & Part 2 ---");
            demonstrateComparison();

            // Clean up
            System.out.println("\n✓ Shutting down Weld container...");
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("Weld CDI Demo Complete!");
        System.out.println("=".repeat(60));
    }

    /**
     * Shows Weld resolving dependencies using CDI annotations.
     */
    private static void demonstrateWeldResolution(WeldContainer container) {
        System.out.println("\nAsking Weld for: UserService");
        System.out.println("Weld will:");
        System.out.println("  1. Find UserServiceImpl (implements UserService)");
        System.out.println("  2. See @Inject on constructor");
        System.out.println("  3. See UserRepository parameter");
        System.out.println("  4. Find InMemoryUserRepository (implements UserRepository)");
        System.out.println("  5. See @ApplicationScoped scope");
        System.out.println("  6. Create singleton and inject");

        // Weld uses .select() to find beans
        UserService userService = container.select(UserService.class).get();

        System.out.println("\n✓ Weld returned fully wired UserService!");
        System.out.println("  Type: " + userService.getClass().getSimpleName());

        // Use the service
        System.out.println("\nUsing the service:");
        User user1 = new User("301", "Grace Hopper", "grace@example.com");
        User user2 = new User("302", "Alan Turing", "alan@example.com");

        userService.registerUser(user1);
        userService.registerUser(user2);

        System.out.println("All users: " + userService.getAllUsers());
    }

    /**
     * Demonstrates that Weld maintains singleton scope for @ApplicationScoped beans.
     */
    private static void demonstrateWeldSingleton(WeldContainer container) {
        System.out.println("\nRequesting UserService again...");

        UserService userService1 = container.select(UserService.class).get();
        UserService userService2 = container.select(UserService.class).get();

        System.out.println("✓ Both instances are the same object: " + (userService1 == userService2));
        System.out.println("  @ApplicationScoped ensures singleton behavior");

        // Verify they share the same data
        System.out.println("\nBoth references see the same repository data:");
        System.out.println("  userService1 getAllUsers: " + userService1.getAllUsers().size() + " users");
        System.out.println("  userService2 getAllUsers: " + userService2.getAllUsers().size() + " users");
    }

    // --Compares all three approaches--
    private static void demonstrateComparison() {
        System.out.println("""

            === COMPARISON: All Three Approaches ===

            Part 1 Manual Constructor Injection:
              yes: Explicit and clear
              yes: No magic, easy to debug
              no: Verbose, must wire everything manually
              no: Hard to scale with many dependencies
              no: No singleton management

            Part 2 - Simple Reflection-based Container:
              yes: Automatic resolution using reflection
              yes: Educational, shows how frameworks work
              yes: Singleton caching built-in
              no: Limited error handling
              no: Assumes single constructor
              no: No lifecycle management

            Part 3 - Weld (CDI):
              yes: Industry standard (Jakarta EE)
              yes: Powerful features: scopes, interceptors, decorators
              yes: Excellent error messages and diagnostics
              yes: Works with @ApplicationScoped, @Singleton, @Dependent
              yes: Type-safe dependency resolution
              yes: Production-ready
              no: Requires beans.xml configuration file
              no: Larger dependency (full CDI implementation)

            === Key Takeaways ===

            Why Constructor Injection?
              - Immutability: dependencies cannot be changed after creation
              - Testability: easy to pass mock implementations
              - Clarity: dependencies visible in constructor signature

            When to use each approach?
              - Manual DI: Learning projects, very small applications
              - Custom Container: Educational, understanding DI principles
              - Weld/Spring: Production code, team projects, enterprise apps
            """);
    }
}
