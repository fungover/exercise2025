package org.example.di.manual;

import org.example.di.common.GreetingsService;

public class ManualMain {
    public static void main(String[] args) {
        InMemoryMessageRepository repo = new InMemoryMessageRepository();
        GreetingsService service = new SimpleGreetingService(repo);
        System.out.println(service.getGreeting("User"));
    }
}
