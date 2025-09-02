package org.example.utils;

import org.example.map.DungeonMap;
import org.example.map.TileType;

public final class SpawnLocator {
    private SpawnLocator() {}

    public static Position findSpawn(DungeonMap map) {
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                if (map.tileAt(x, y).getType() == TileType.SPAWN) {
                    return new Position(x, y);
                }
            }
        }
        // Fallback if no SPAWN found (shouldn’t happen with your generator)
        return new Position(1, 1);
    }
}
