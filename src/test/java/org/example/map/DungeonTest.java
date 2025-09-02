package org.example.map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DungeonTest {

    @Test
    void border_is_wall_and_inside_is_empty_or_exit() {
        Dungeon dungeon = new Dungeon();

        // Hörn = WALL
        assertEquals(TileType.WALL, dungeon.get(0, 0).getType());
        assertEquals(TileType.WALL, dungeon.get(9, 0).getType());
        assertEquals(TileType.WALL, dungeon.get(0, 9).getType());
        assertEquals(TileType.WALL, dungeon.get(9, 9).getType());

        // Några innerrutor = EMPTY
        assertEquals(TileType.EMPTY, dungeon.get(1, 1).getType());
        assertEquals(TileType.EMPTY, dungeon.get(8, 1).getType());
        assertEquals(TileType.EMPTY, dungeon.get(5, 4).getType());

        // Exit-rutan
        assertEquals(TileType.EXIT, dungeon.get(8, 8).getType());
    }

    @Test
    void inBounds_works() {
        Dungeon dungeon = new Dungeon();
        assertTrue(dungeon.inBounds(0, 0));
        assertTrue(dungeon.inBounds(9, 9));
        assertFalse(dungeon.inBounds(-1, 0));
        assertFalse(dungeon.inBounds(10, 0));
        assertFalse(dungeon.inBounds(0, -1));
        assertFalse(dungeon.inBounds(0, 10));
    }
}
