package org.example.admin;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import org.example.users.UserCreatedEvent;
import org.example.users.UserService;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AdminNotificationService {
    Logger logger = Logger.getLogger(AdminNotificationService.class);
    public void handleUserCreatedEvents(@ObservesAsync UserCreatedEvent event){
        logger.info("Trying to notify Admin...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
    }
}
