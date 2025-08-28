package org.example.utils;

import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.map.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerator {
    private static final Random random = new Random();

    public static Position findRandomFloorPosition(Dungeon dungeon) {
        int attempts = 0;
        while (attempts < 100) {
            int x = random.nextInt(dungeon.getColumns());
            int y = random.nextInt(dungeon.getRows());

            if (dungeon.getTile(x, y).getType() == TileType.FLOOR) {
                return new Position(x, y);
            }
            attempts++;
        }
        return null;
    }

    public static List<Position> getRandomFloorPositions(int count, Dungeon dungeon) {
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Position pos = findRandomFloorPosition(dungeon);
            if (pos != null) {
                positions.add(pos);
            }
        }
        return positions;
    }
}
