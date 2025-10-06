package org.example;

public class App {
    public static void main(String[] args) {
        // Skapa container och bind interfaces till implementationer
        SimpleContainer container = new SimpleContainer()
                .bind(MessageRepository.class, InMemoryMessageRepository.class)
                .bind(GreetingService.class, SimpleGreetingService.class);

        // Hämta top-level klass (GreetingService)
        GreetingService service = container.get(GreetingService.class);

        System.out.println(service.greet("World"));
    }
}
