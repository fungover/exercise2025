package org.example.di.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.di.common.MessageRepository;

@ApplicationScoped
public class CdiMessageRepository implements MessageRepository {

    private static int counter = 0;

    public CdiMessageRepository() {
        counter++;
        System.out.println("CdiMessageRepository created, count: " + counter);
    }

    public static int getCounter() {
        return counter;
    }

    public static void resetCounter() {
        counter = 0;
    }

    @Override
    public String getMessage() {
        return "Hi there";
    }
}
