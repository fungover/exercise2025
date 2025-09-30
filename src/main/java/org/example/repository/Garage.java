package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.entities.Vehicle;

import java.util.List;

@ApplicationScoped
public class Garage implements VehicleRepository {
    private final List<Vehicle> vehicles;

    public Garage(List<Vehicle> listType) {
        if (listType == null) {
            throw new IllegalArgumentException("Vehicle list cannot be null");
        }
        this.vehicles = listType;
    }

    @Override
    public void store(Vehicle v) {
        System.out.println("Storing: " + v);
        vehicles.add(v);
    }

    @Override
    public void display() {
        System.out.println("Vehicles in Garage:");
        int pos = 1;
        for (Vehicle v : vehicles) {
            System.out.println(pos + ": " + v);
            pos++;
        }
    }

}
