package org.example;

public class App {
    public static void main(String[] args) {
        // Manuell "wiring" via konstruktorer
        MessageRepository repo = new InMemoryMessageRepository();
        GreetingService service = new SimpleGreetingService(repo);

        System.out.println(service.greet("World"));
    }
}
