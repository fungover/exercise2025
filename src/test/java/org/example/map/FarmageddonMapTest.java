package org.example.map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FarmageddonMapTest {

    @Test
    public void testMapInitialization() {
        FarmageddonMap map = new FarmageddonMap(10, 10);

        assertEquals(10, map.getWidth());
        assertEquals(10, map.getHeight());
    }

    @Test
    public void testPlayerStartPosition() {
        FarmageddonMap map = new FarmageddonMap(10, 10);
        Tile startTile = map.getTile(0, 0);

        assertEquals(Tile.Type.PLAYER_START, startTile.getType());
    }

    @Test
    public void testValidTileCheck() {
        FarmageddonMap map = new FarmageddonMap(10, 10);

        // Find a PATH threw iteration
        boolean foundPath = false;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (map.getTile(x, y).getType() == Tile.Type.PATH) {
                    assertTrue(map.isValidTile(x, y));
                    foundPath = true;
                    break;
                }
            }
            if (foundPath) break;
        }

        assertTrue(foundPath); // make sure we found a PATH

        // Test out of bounds
        assertFalse(map.isValidTile(-1, 0)); // Out of bounds
        assertFalse(map.isValidTile(0, 0)); // Player start, not PATH

        // Test WALL tile (if there is one)
        boolean foundWall = false;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (map.getTile(x, y).getType() == Tile.Type.WALL) {
                    assertFalse(map.isValidTile(x, y));
                    foundWall = true;
                    break;
                }
            }
            if (foundWall) break;
        }
    }

    @Test
    public void testEnemyPlacementAtFixedCoordinates() {
        FarmageddonMap map = new FarmageddonMap(10, 10);

        int[][] expectedEnemyCoords = {
                {2, 2},
                {4, 3},
                {6, 1},
                {1, 5}
        };

        for (int[] coord : expectedEnemyCoords) {
            Tile tile = map.getTile(coord[0], coord[1]);
            assertEquals(Tile.Type.ENEMY, tile.getType(), "Tile at (" + coord[0] + "," + coord[1] + ") should be ENEMY");
            assertNotNull(tile.getEnemy(), "Enemy should be present at (" + coord[0] + "," + coord[1] + ")");
        }
    }
}