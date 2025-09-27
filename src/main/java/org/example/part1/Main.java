package org.example.part1;

import org.example.persistence.DataRepository;
import org.example.persistence.DatabaseRepository;
import org.example.persistence.InMemoryRepository;
import org.example.service.AdvancedDataService;
import org.example.service.DataService;
import org.example.service.SimpleDataService;


public class Main {
     static void main(String[] args) {
        DataRepository dbRepo = new DatabaseRepository();
        DataRepository memRepo = new InMemoryRepository();

        DataService simple = new SimpleDataService(dbRepo);
        DataService advanced = new AdvancedDataService(memRepo);

        simple.process("Hello Part 1!");
        advanced.process("Hello Part 1!");
    }
}
