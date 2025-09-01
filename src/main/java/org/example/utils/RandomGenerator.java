package org.example.utils;

import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.map.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerator {
    private final Random random;

    //Constructor for testing purposes
    public RandomGenerator() {
        this.random = new Random();
    }

    public Position findRandomFloorPosition(Dungeon dungeon) { // Find a random floor position in the dungeon
        int attempts = 0; // Limit attempts to avoid infinite loops
        while (attempts < 100) { // Setting a limit to prevent infinite loops
            int x = random.nextInt(dungeon.getColumns()); // Random x coordinate
            int y = random.nextInt(dungeon.getRows()); // Random y coordinate

            if (dungeon.getTile(x, y).getType() == TileType.FLOOR) { // Check if the tile is a floor
                return new Position(x, y); // Return the position if it's a floor
            }
            attempts++; // Increment attempts
        }
        return null; // Return null if no floor position is found after max attempts
    }

    public List<Position> getRandomFloorPositions(int count, Dungeon dungeon) { // Get multiple random floor positions
        List<Position> positions = new ArrayList<>(); // List to hold the positions
        for (int i = 0; i < count; i++) { // Loop to find the required number of positions
            Position pos = findRandomFloorPosition(dungeon); // find a position
            if (pos != null) { // Check if a valid position was found
                positions.add(pos); // Add position x,y to the list
            }
        }
        return positions; // Return the list of positions
    }

    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
