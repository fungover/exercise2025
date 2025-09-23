package org.example.persistence;

import jakarta.enterprise.inject.Vetoed;


// Vetoed disables this for CDI
@Vetoed
public class InMemoryOrderRepository implements OrderRepository {

    public InMemoryOrderRepository() {
        System.out.println("InMemoryOrderRepository created");
    }

    @Override public void saveOrder(String orderId) {
        System.out.println("Saving order " + orderId + " to memory");
    }

    @Override public String findOrderById(String orderId) {
        System.out.println("Finding order " + orderId + " from memory");
        return "Order found: " + orderId + " (Pending)";
    }
}
