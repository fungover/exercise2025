package org.example;

import org.example.container.Container;
import org.example.entities.Car;
import org.example.entities.Motorcycle;
import org.example.repository.Garage;
import org.example.repository.VehicleRepository;
import org.example.service.CdiVehicleService;
import org.example.service.VehicleProcessor;
import org.example.service.VehicleService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // Part 1
        VehicleRepository repository = new Garage();
        VehicleProcessor processor = new VehicleService(repository);

        Car volvo = new Car("Volvo", "V50",
                "Red", 2005);
        Motorcycle harley = new Motorcycle("Harley Davidson", "Nightster",
                "Black", 2024);

        processor.process(volvo);
        processor.process(harley);

        // Part 2
        Car bmw = Container.createInstance(Car.class, "BMW",
                "M3", "Blue", 2013);
        System.out.println(bmw);

        // Part 3
        Weld weld = new Weld();
        System.out.println(); // Line break
        try (WeldContainer container = weld.initialize()) {
            CdiVehicleService service = container
                    .select(CdiVehicleService.class).get();
            System.out.println();
            service.process(volvo);
        }
    }
}
