package org.example;

import org.example.map.Dungeon;
import org.example.map.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DungeonTest {

    @Test
    void testDungeonHasValidStructure() {
        Dungeon dungeon = new Dungeon(8, 10); // Create an 8x10 dungeon

        assertEquals(TileType.WALL, dungeon.getTile(0, 0).getType()); // Top-left corner which should be a wall
        assertEquals(TileType.WALL, dungeon.getTile(7, 0).getType()); // Top-right corner which should be a wall

        assertEquals(TileType.FLOOR, dungeon.getTile(1, 1).getType()); // Inside the dungeon, on a floor tile.

        boolean doorFound = false; // Check if there's at least one door in the dungeon
        for (int y = 0; y < dungeon.getRows(); y++) { // Loop through each row
            for (int x = 0; x < dungeon.getColumns(); x++) { // Loop through each column
                if (dungeon.getTile(x, y).getType() == TileType.DOOR) { // if a door is found, we set doorFound to true
                    doorFound = true;
                    break;
                }
            }
        }
        assertTrue(doorFound, "Dungeon have at least one door"); // Assert that at least one door was found
    }

}
