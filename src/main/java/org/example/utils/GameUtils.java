package org.example.utils;

import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.map.Tile;

import java.util.Random;

public class GameUtils {


    public static Position randomFloorPosition(Dungeon dungeon, Random random) {
        int w = dungeon.getWidth();
        int h = dungeon.getHeight();

        if (w <= 0 || h <= 0) {
            throw new IllegalArgumentException("Dungeon width and height must be > 0");
        }

        int maxAttempts = Math.max(1, w * h * 2);

        for (int i = 0; i < maxAttempts; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            Position pos = new Position(x, y);
            if (isValidTile(dungeon, pos)) {
                return pos;
            }
        }

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Position pos = new Position(x, y);
                if (isValidTile(dungeon, pos)) {
                    return pos;
                }
            }
        }

        throw new IllegalStateException("No valid floor position found in dungeon");
    }

    private static boolean isValidTile(Dungeon dungeon, Position pos) {
        Tile tile = dungeon.getTile(pos);
        return tile != null && tile.isWalkable() && tile.getEnemy() == null && tile.getItem() == null;
    }
}