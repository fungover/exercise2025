package org.example.service;

import org.example.persistence.OrderRepository;

public class StandardOrderService implements OrderService {
    private final OrderRepository orderRepository;

    public StandardOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        System.out.println("StandardOrderService created with repository: "
          //will give us the name of what we injected with
          + orderRepository.getClass()
                           .getSimpleName());
    }

    @Override public void processOrder(String orderId) {
        System.out.println("Processing order: " + orderId);
        orderRepository.saveOrder(orderId);
    }

    @Override public String getOrderStatus(String orderId) {
        return orderRepository.findOrderById(orderId);
    }

}
