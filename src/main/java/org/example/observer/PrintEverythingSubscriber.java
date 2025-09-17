package org.example.observer;

public class PrintEverythingSubscriber implements Subscriber{
    @Override
    public void update(String context) {
        System.out.println("Subscriber received: " + context);
    }
}
