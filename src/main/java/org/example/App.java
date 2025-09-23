package org.example;

import org.example.container.SimpleDIContainer;
import org.example.persistence.DatabaseOrderRepository;
import org.example.persistence.InMemoryOrderRepository;
import org.example.persistence.OrderRepository;
import org.example.service.EmailNotificationService;
import org.example.service.NotificationService;
import org.example.service.OrderService;
import org.example.service.StandardOrderService;

public class App {
    public static void main(String[] args) {
        //made them in to methods to easier look over each part
        runPart1();
        System.out.println("\n" + "=".repeat(30) + "\n");
        runPart2();


    }

    private static void runPart1() {
        System.out.println("=== Part 1: Manual Dependency Injection ===");

        // 1. create the dependency
        System.out.println("Creating with DatabaseOrderRepository");
        OrderRepository repository = new DatabaseOrderRepository();
        NotificationService emailService = new EmailNotificationService();
        // 2. inject the dependency into the service
        OrderService orderService = new StandardOrderService(repository,
          emailService);
        // 3. use service
        System.out.println("\n---Ready to receive order--");
        orderService.processOrder("order-001");
        System.out.println(orderService.getOrderStatus("order-001"));

        System.out.println("\n---Changing injection to InMemoryOrderRepository---");

        OrderRepository memoryRepository = new InMemoryOrderRepository();
        NotificationService emailService2 = new EmailNotificationService();

        OrderService orderService2 = new StandardOrderService(memoryRepository,
          emailService2);
        System.out.println("\n---Ready to receive order--");
        orderService2.processOrder("order-002");
        System.out.println(orderService2.getOrderStatus("order-002"));

    }

    private static void runPart2() {
        System.out.println("=== Part 2: Simple DI Container ===\n");

        //Create our simple container
        SimpleDIContainer container = new SimpleDIContainer();

        // Register interfaces and their implementations
        System.out.println("Registering dependencies...");
        container.register(OrderRepository.class, DatabaseOrderRepository.class);
        container.register(NotificationService.class,
          EmailNotificationService.class);

        //shows whats inside our container
        container.showRegistry();

        // ask container for top-level service
        System.out.println(
          "Requesting OrderService (automatic dependency resolution)..");
        OrderService orderService = container.getInstance(
          StandardOrderService.class);

        // use the service
        System.out.println("\nUsing the service:");
        orderService.processOrder("order-001");
        System.out.println(orderService.getOrderStatus("order-001"));


        //asking for same service again (should be cached)
        System.out.println("\nRequesting same service again (should use cached):");
        OrderService orderService2 = container.getInstance(
          StandardOrderService.class);
        System.out.println("Same instance? " + (orderService == orderService2));
    }
}

