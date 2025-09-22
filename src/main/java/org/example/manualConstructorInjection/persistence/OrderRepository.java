package org.example.manualConstructorInjection.persistence;

public interface OrderRepository {
    void saveOrder(String orderId);

    String findOrderById(String orderId);
}
