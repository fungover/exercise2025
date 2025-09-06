package org.example.utils;

import org.example.map.DungeonMap;
import org.example.map.TileType;

import static org.example.utils.Guard.notNull;

public final class SpawnLocator {
    private SpawnLocator() {
    }

    public static Position findSpawn(DungeonMap map) {
        notNull(map, "map");
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                if (map.tileAt(x, y).getType() == TileType.SPAWN) {
                    return new Position(x, y);
                }
            }
        }
        throw new IllegalStateException("No SPAWN tile found in map");
    }
}
