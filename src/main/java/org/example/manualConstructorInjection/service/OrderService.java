package org.example.manualConstructorInjection.service;

public interface OrderService {
    void processOrder(String orderId);

    String getOrderStatus(String orderId);
}
