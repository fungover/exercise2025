package org.example;

import org.example.persistence.DatabaseOrderRepository;
import org.example.persistence.InMemoryOrderRepository;
import org.example.persistence.OrderRepository;
import org.example.service.OrderService;
import org.example.service.StandardOrderService;

public class App {
    public static void main(String[] args) {
        // 1. create the dependency
        OrderRepository repository = new DatabaseOrderRepository();

        // 2. inject the dependency into the service
        OrderService orderService = new StandardOrderService(repository);

        // 3. use service
        System.out.println("---Ready to receive order--");
        orderService.processOrder("order-001");
        String status = orderService.getOrderStatus("order-001");
        System.out.println("Status " + status);

        System.out.println("---Changing injection---");

        OrderRepository memoryRepository = new InMemoryOrderRepository();
        OrderService orderService2 = new StandardOrderService(memoryRepository);

        orderService2.processOrder("order-002");
        String status2 = orderService2.getOrderStatus("order-002");
        System.out.println("Status " + status2);
    }
}

