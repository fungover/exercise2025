package org.example.service;

import org.example.map.Dungeon;

public class FloorService {
    private final int totalFloors;
    private int currentFloor;

    public FloorService(int totalFloors) {
        if (totalFloors < 1) {
            throw new IllegalArgumentException("totalFloors must be >= 1");
        }
        this.totalFloors = totalFloors;
        this.currentFloor = 1; // start på våning 1
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public boolean isFinalFloor() {
        return currentFloor == totalFloors;
    }

    public Dungeon advance() {
        if (currentFloor >= totalFloors) {
            throw new IllegalStateException("Already on final floor");
        }
        currentFloor++;
        return new Dungeon(currentFloor);
    }
}