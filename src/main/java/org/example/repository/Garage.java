package org.example.repository;

import org.example.entities.Vehicle;

import java.util.List;

public class Garage implements VehicleRepository {
    private final List<Vehicle> vehicles;

    public Garage(List<Vehicle> listType) {
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
