package org.example;

import org.example.container.Container;
import org.example.persistence.DataBasePersistence;
import org.example.persistence.FilePersistence;
import org.example.persistence.PersistenceLayer;
import org.example.service.AdvancedService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;


public class App {

    public static void main(String[] args) throws Exception {
        Container container = new Container();

        // FilePersistence
        container.bind(PersistenceLayer.class, FilePersistence.class);
        AdvancedService fileService = container.create(AdvancedService.class);
        fileService.save("file-data.txt");

        System.out.println("\n--- Switching to database ---\n");

        // DataBasePersistence-variant
        container.bind(PersistenceLayer.class, DataBasePersistence.class);
        AdvancedService dbService = container.create(AdvancedService.class);
        dbService.save("db-data.txt");

        Car car = container.create(Car.class);
        Transmission transmission = container.create(Transmission.class);
        System.out.println(car);
        System.out.println(transmission);

        Weld weld = new Weld();
        try (WeldContainer weldContainer = weld.initialize()) {
            AdvancedService service = weldContainer.select(AdvancedService.class).get();
            Car car2 = weldContainer.select(Car.class).get();

            System.out.println(car2.toString() + " has been created.");
            service.save("TestData1");
            service.update("TestData2");
            service.delete("OldData");
            service.processData("Processing...");
        }
    }
}

// TODO: Fix so that AdvancedService can run both FilePersistence and DataBasePersistence