package org.example.email;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import org.example.users.UserCreatedEvent;

@ApplicationScoped
public class EmailService {

    // Listener
    public void onUserCreated(@ObservesAsync UserCreatedEvent event) {
        System.out.println("Email Service got an event about user created: " + event.getUsername());
    }

}
