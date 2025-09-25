package org.example;

import org.example.entities.Car;
import org.example.entities.Motorcycle;
import org.example.repository.Garage;
import org.example.repository.VehicleRepository;
import org.example.service.VehicleProcessor;
import org.example.service.VehicleService;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // Dependency injection with vehicles
        VehicleRepository repository = new Garage(new ArrayList<>());
        VehicleProcessor processor = new VehicleService(repository);

        processor.process(new Car("Volvo", "V50",
                "Red", 2005));
        processor.process(new Motorcycle("Harley Davidson", "Nightster",
                "Black", 2024));
    }
}
