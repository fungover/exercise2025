package org.example.map;

import org.example.entities.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DungeonGeneratorTest {

    @Test void testBasicDungeonGeneration() {
        int width = 10;
        int height = 8;
        var dungeon = new DungeonGenerator(width, height);

        //checks grid size
        assertEquals(width, dungeon.getWidth());
        assertEquals(height, dungeon.getHeight());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {//checks every tile
                assertNotNull(dungeon.getTile(x, y));
            }
        }

        //checks so there are walls around
        for (int x = 0; x < width; x++) {
            assertTrue(dungeon.getTile(x, 0).isWall());
            assertTrue(dungeon.getTile(x, height - 1).isWall());
        }

        for (int y = 0; y < height; y++) {
            assertTrue(dungeon.getTile(0, y).isWall());
            assertTrue(dungeon.getTile(width - 1, y).isWall());
        }

        //check players starting position is inside the map and not in wall
        int px = dungeon.getPlayerStartX();
        int py = dungeon.getPlayerStartY();


        assertTrue(px > 0 && px < width - 1);
        assertTrue(py > 0 && py < height - 1);

        Tile playerStart = dungeon.getTile(px, py);
        assertFalse(playerStart.isWall());

    }
}