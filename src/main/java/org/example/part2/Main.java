package org.example.part2;

import org.example.di.Container;
import org.example.persistence.DataRepository;
import org.example.persistence.DatabaseRepository;
import org.example.persistence.InMemoryRepository;
import org.example.service.AdvancedDataService;
import org.example.service.DataService;
import org.example.service.SimpleDataService;

public class Main {
     public static void main(String[] args) {
         Container container = new Container();

        // Test with InMemoryRepository + AdvancedDataService
         container.bind(DataRepository.class, InMemoryRepository.class);
         container.bind(DataService.class, AdvancedDataService.class);

         DataService service = container.getInstance(DataService.class);
         service.process("Hello Part 2 with AdvancedDataService + InMemoryRepository");
    }
}
