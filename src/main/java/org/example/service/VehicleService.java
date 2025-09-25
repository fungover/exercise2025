package org.example.service;

import org.example.entities.Vehicle;
import org.example.repository.VehicleRepository;

public record VehicleService(VehicleRepository vehicles) implements VehicleProcessor {

    @Override
    public void process(Vehicle v) {
        System.out.println("Initiate Process");
        System.out.println("Processing: " + v);
        vehicles.store(v);
        vehicles.display();
        System.out.println("Done");
        System.out.println(); // Line break
    }
}
