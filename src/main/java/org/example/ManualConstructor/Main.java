package org.example.ManualConstructor;

import org.example.ManualConstructor.persistence.DataBasePersistence;
import org.example.ManualConstructor.persistence.FilePersistence;
import org.example.ManualConstructor.service.AdvancedService;

import java.time.format.TextStyle;

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
