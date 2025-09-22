package org.example.part2;

import org.example.di.Container;
import org.example.persistence.DataRepository;
import org.example.persistence.DatabaseRepository;
import org.example.service.DataService;
import org.example.service.SimpleDataService;

public class Main {
     public static void main(String[] args) {
         Container container = new Container();


         container.bind(DataRepository.class, DatabaseRepository.class);
         // Only ask for the interface
         DataService service = container.getInstance(SimpleDataService.class);
         service.process("Hello Part 2!");
    }
}
