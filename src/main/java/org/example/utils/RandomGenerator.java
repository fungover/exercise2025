package org.example.utils;

import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.map.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerator {
    private final Random random;


    public RandomGenerator() {
        this.random = new Random();
    }

    //Constructor used for Junit testing.
    public RandomGenerator(Random random) {
        this.random = random;
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

    public List<Position> getRandomFloorPositions(int count, Dungeon dungeon) {
        List<Position> positions = new ArrayList<>();
        int attempts = 0;

        while (positions.size() < count && attempts < 100) {
            Position pos = findRandomFloorPosition(dungeon);
            if (pos != null && !positions.contains(pos)) { // Ensure no duplicates on the same position
                positions.add(pos);
            }
            attempts++;
        }
        return positions;
    }

    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
