package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.entities.Vehicle;
import org.example.repository.VehicleRepository;

@ApplicationScoped
public class CdiVehicleService implements VehicleProcessor {
    private final VehicleRepository vehicles;

    @Inject
    public CdiVehicleService(VehicleRepository vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public void process(Vehicle v) {
        System.out.println("Initiate Process with CDI Vehicle");
        System.out.println("Processing: " + v);
        vehicles.store(v);
        vehicles.display();
        System.out.println("Done");
        System.out.println(); // Line break
    }
}
