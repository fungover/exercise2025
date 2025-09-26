package org.example;

import org.example.persistence.DataBasePersistence;
import org.example.persistence.FilePersistence;
import org.example.service.AdvancedService;

public class Main {
    void main() {
        // running with FilePersistence
        AdvancedService service = new AdvancedService(new FilePersistence());
        service.processData("This is a test");
        service.save("example.txt");
        service.update("Hello world!");
        service.delete("example.txt");

        // running with DataBasePersistence
        AdvancedService dbService = new AdvancedService(new DataBasePersistence());
        dbService.processData("\nThis is a test for the database");
        dbService.save("example.txt");
        dbService.update("Hello world!");
        dbService.delete("example.txt");


    }
}
