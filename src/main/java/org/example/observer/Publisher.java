package org.example.observer;

import java.util.HashSet;
import java.util.Set;

public class Publisher {

    Set<Subscriber> subscribers = new HashSet<>();

    public void subscribe(Subscriber s){
        subscribers.add(s);
    }

    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    public void notifySubscribers(String context) {
        subscribers.forEach(subscriber -> subscriber.update(context));
    }

    static void main() {
        Publisher publisher = new Publisher();
        Subscriber subscriber = new PrintEverythingSubscriber();
        Subscriber subscriber2 = new PrintEverythingSubscriber();

        publisher.subscribe(subscriber);
        publisher.subscribe(subscriber2);
        publisher.notifySubscribers("Hello World");

        publisher.unsubscribe(subscriber);
        publisher.notifySubscribers("No one listens to me...");
    }
}
