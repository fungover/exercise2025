package org.example.map;

import org.example.entities.Tile;
import org.example.entities.TileType;
import org.example.entities.enemies.Goblin;
import org.example.entities.enemies.Troll;
import org.example.entities.items.Potion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicMapGeneratorTest {
    @Test
    void testGenerate_ValidMap_CorrectDimensionsAndEntities() {
        BasicMapGenerator generator = new BasicMapGenerator();
        Tile[][] tiles = generator.generate(5, 5);

        // Check dimensions
        assertEquals(5, tiles.length, "Map should have height 5");
        assertEquals(5, tiles[0].length, "Map should have width 5");

        // Check border walls
        for (int x = 0; x < 5; x++) {
            assertEquals(TileType.WALL, tiles[0][x].getType(), "Top border should be walls");
            assertEquals(TileType.WALL, tiles[4][x].getType(), "Bottom border should be walls");
        }
        for (int y = 0; y < 5; y++) {
            assertEquals(TileType.WALL, tiles[y][0].getType(), "Left border should be walls");
            assertEquals(TileType.WALL, tiles[y][4].getType(), "Right border should be walls");
        }

        // Count entities
        int enemyCount = 0, potionCount = 0;
        for (int y = 1; y < 4; y++) {
            for (int x = 1; x < 4; x++) {
                Tile tile = tiles[y][x];
                if (tile.getType() == TileType.ENEMY && (tile.getEnemy() instanceof Goblin || tile.getEnemy() instanceof Troll)) {
                    enemyCount++;
                } else if (tile.getType() == TileType.ITEM && tile.getItem() instanceof Potion) {
                    potionCount++;
                }
            }
        }
        assertEquals(1, enemyCount, "Should have 1 Enemy (Goblin or Troll)");
        assertEquals(1, potionCount, "Should have 1 Potion");

        // Ensure no overlaps at (2, 3) (player start)
        assertEquals(TileType.EMPTY, tiles[3][2].getType(), "Player start (2, 3) should be empty");
    }

    @Test
    void testGenerate_SmallMap_NoEntities() {
        BasicMapGenerator generator = new BasicMapGenerator();
        Tile[][] tiles = generator.generate(2, 2);

        // Check dimensions
        assertEquals(2, tiles.length, "Map should have height 2");
        assertEquals(2, tiles[0].length, "Map should have width 2");

        // Check all walls
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                assertEquals(TileType.WALL, tiles[y][x].getType(), "All tiles should be walls in 2x2 map");
            }
        }
    }
}