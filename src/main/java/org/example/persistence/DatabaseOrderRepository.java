package org.example.persistence;

public class DatabaseOrderRepository implements OrderRepository {

    public DatabaseOrderRepository() {
        System.out.println("Database Order Repository created");
    }

    @Override public void saveOrder(String orderId) {
        System.out.println("Saving order " + orderId + " to database");
    }

    @Override public String findOrderById(String orderId) {
        System.out.println("Finding order " + orderId + " from database");
        return "Order found: " + orderId + " (CONFIRMED)";
    }
}
