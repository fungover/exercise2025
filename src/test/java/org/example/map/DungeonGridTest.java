package org.example.map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DungeonGridTest {
    int width = 10;
    int height = 10;

    @Test
    @DisplayName("Generate a 10x10 map. (Map generation (basic validation))")
    void testDungeonGridGeneration(){
        DungeonGrid grid = DungeonGrid.createDungeonGrid(width, height);

        assertEquals(width, grid.getWidth());
        assertEquals(height, grid.getHeight());

        //Confirm wall and door and exit tiles are present
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    Tile.TileType type = grid.getTiles()[x][y].getType();
                    assertTrue(type == Tile.TileType.WALL || type == Tile.TileType.DOOR || type == Tile.TileType.EXIT);
                }
            }
        }

        //Confirm floor tiles are present
        for (int x = 1; x < width -1; x++) {
            for (int y = 1; y < height -1; y++) {
                assertEquals(Tile.TileType.FLOOR, grid.getTiles()[x][y].getType());
            }
        }
    }

}
