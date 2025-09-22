package org.example;

import org.example.persistence.DataRepository;
import org.example.persistence.DatabaseRepository;
import org.example.service.DataService;
import org.example.service.SimpleDataService;

public class App {
    public static void main(String[] args) {
        // Create dependency
        DataRepository repository = new DatabaseRepository();
        // Send dependency to constructor
        DataService service = new SimpleDataService(repository);
        // Use service
        service.process("Hello Dependency Injection!");
    }
}
