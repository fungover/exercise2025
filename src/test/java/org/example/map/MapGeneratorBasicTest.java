package org.example.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class MapGeneratorBasicTest {

    @Test
    void generate_contains_SPAWN_and_at_least_some_floor_tiles() {
        final MapGenerator generator = new MapGenerator(123L, 4, 8, 5, 1, 500);
        final DungeonMap dungeonMap = generator.generate(40, 15);

        boolean hasSpawnTile = false;
        int floorTileCount = 0;

        for (int y = 0; y < dungeonMap.height(); y++) {
            for (int x = 0; x < dungeonMap.width(); x++) {
                final TileType tileType = dungeonMap.tileAt(x, y).getType();
                if (tileType == TileType.SPAWN) hasSpawnTile = true;
                if (tileType == TileType.FLOOR) floorTileCount++;
            }
        }

        assertTrue(hasSpawnTile, "Map should contain a SPAWN tile");
        assertTrue(floorTileCount > 0, "Map should contain some FLOOR tiles");
    }
}
