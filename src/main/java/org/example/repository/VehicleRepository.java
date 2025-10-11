package org.example.repository;

import org.example.entities.Vehicle;

public interface VehicleRepository {
    void store(Vehicle v);
    void display();
}
