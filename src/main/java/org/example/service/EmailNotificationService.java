package org.example.service;

public class EmailNotificationService implements NotificationService {
    public EmailNotificationService() {
        System.out.println("Creating EmailNotificationService");
    }

    @Override public void sendNotification(String message) {
        System.out.println("Sending email: " + message);
    }

}
