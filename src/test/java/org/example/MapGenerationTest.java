package org.example;

import map.DungeonMap;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class MapGenerationTest {

    @Test
    void generate_createsWalkableTile_andRandomEmptyFloorFindsSomething() {
        DungeonMap map = new DungeonMap(40, 20);
        map.generate(10, 4, 8);

        Position spawn = map.findFirstWalkable();
        assertNotNull(spawn, "No walkable tile after generation");
        assertTrue(map.inBounds(spawn.getX(), spawn.getY()));
        assertTrue(map.getTile(spawn.getX(), spawn.getY()).isWalkable());

        Position empty = map.randomEmptyFloor(500);
        assertNotNull(empty, "No empty floor found");
        assertTrue(map.getTile(empty.getX(), empty.getY()).isEmpty());
    }

    @Test
    void getTile_throwsOnOutOfBounds_andInBoundsConsistent() {
        DungeonMap map = new DungeonMap(5, 5);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                assertTrue(map.inBounds(x, y));
                assertNotNull(map.getTile(x, y));
            }
        }

        assertFalse(map.inBounds(-1, 0));
        assertFalse(map.inBounds(5, 0));
        assertFalse(map.inBounds(0, -1));
        assertFalse(map.inBounds(0, 5));

        assertThrows(IndexOutOfBoundsException.class, () -> map.getTile(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> map.getTile(5, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> map.getTile(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> map.getTile(0, 5));
    }
}