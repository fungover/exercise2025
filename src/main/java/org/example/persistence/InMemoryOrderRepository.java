package org.example.persistence;

public class InMemoryOrderRepository implements OrderRepository {

    @Override public void saveOrder(String orderId) {
        System.out.println("Saving order " + orderId + " to memory");
    }

    @Override public String findOrderById(String orderId) {
        System.out.println("Finding order " + orderId + " from memory");
        return "Order found: " + orderId + " (Pending)";
    }
}
