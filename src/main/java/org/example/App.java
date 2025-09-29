package org.example;

import org.example.container.Container;
import org.example.entities.Car;
import org.example.entities.Motorcycle;
import org.example.repository.Garage;
import org.example.repository.VehicleRepository;
import org.example.service.VehicleProcessor;
import org.example.service.VehicleService;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // Part 1
        VehicleRepository repository = new Garage(new ArrayList<>());
        VehicleProcessor processor = new VehicleService(repository);

        processor.process(new Car("Volvo", "V50",
                "Red", 2005));
        processor.process(new Motorcycle("Harley Davidson", "Nightster",
                "Black", 2024));

        // Part 2
        Car bmw = Container.createInstance(Car.class, "BMW",
                "M3", "Blue", 2013);
        System.out.println(bmw);
    }
}
