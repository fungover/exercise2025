package org.example.service;


import jakarta.enterprise.context.ApplicationScoped;

// @ApplicationScoped means this will be a singleton
// CDI will create one instance and reuse it everywhere
@ApplicationScoped
public class EmailNotificationService implements NotificationService {

    //@inject is not needed here cuz it has no dependencies
    // Default constructor - CDI can use this since no dependencies needed
    public EmailNotificationService() {
        System.out.println("Creating EmailNotificationService");
    }

    @Override public void sendNotification(String message) {
        System.out.println("Sending email: " + message);
    }

}
