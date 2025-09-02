package org.example.service;

import org.example.map.Dungeon;

public class FloorService {
    private final int maxFloors;
    private int currentFloor = 1;

    public FloorService(int maxFloors) {
        this.maxFloors = maxFloors;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public boolean isFinalFloor() {
        return currentFloor >= maxFloors;
    }

    public Dungeon advance() {
        if (isFinalFloor()) {
            throw new IllegalStateException("Already on final floor.");
        }
        currentFloor++;
        return new Dungeon();
    }
}
