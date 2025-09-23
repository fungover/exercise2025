package org.example.service;

import org.example.persistence.OrderRepository;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;


// @ApplicationScoped means: "Create ONE instance of this class for the entire 
// application"
@ApplicationScoped
public class StandardOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    // @Inject tells CDI: "Please inject dependencies into this constructor"
    // CDI will automatically find implementations of OrderRepository and
    // NotificationService
    @Inject public StandardOrderService(OrderRepository orderRepository,
                                        NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
        System.out.println("StandardOrderService created with repository: "
          //will give us the name of what we injected with
          + orderRepository.getClass()
                           .getSimpleName());
    }

    @Override public void processOrder(String orderId) {
        System.out.println("Processing order: " + orderId);
        orderRepository.saveOrder(orderId);
        notificationService.sendNotification(
          "Order " + orderId + " has been processed");
    }

    @Override public String getOrderStatus(String orderId) {
        return orderRepository.findOrderById(orderId);
    }

}
