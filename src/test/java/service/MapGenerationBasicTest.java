package service;

import entities.Player;
import map.Dungeon;
import map.Tile;
import map.TileType;
import org.junit.jupiter.api.Test;
import utils.Rng;

import static org.junit.jupiter.api.Assertions.*;

class MapGenerationBasicTest {

    @Test
    void startExistsAndIsWalkable() {
        Player p = new Player("Tester", 20, 0, 0);
        Dungeon d = MapGenerator.createInitialWorld(10, 10, p, new Rng(42));

        Tile start = d.getTile(p.getX(), p.getY());
        assertNotNull(start, "Start tile should exist");
        // In our generator we mark START and ensure it's not a wall
        assertEquals(TileType.START, start.getType(), "Player should start on START tile");
        assertTrue(d.isWalkable(p.getX(), p.getY()), "START should be walkable");
    }

    @Test
    void enemyAndItemCountsWithinExpectedRange() {
        Player p = new Player("Tester", 20, 0, 0);
        Dungeon d = MapGenerator.createInitialWorld(10, 10, p, new Rng(123));

        int enemies = 0;
        int items = 0;

        for (int y = 0; y < d.getHeight(); y++) {
            for (int x = 0; x < d.getWidth(); x++) {
                Tile t = d.getTile(x, y);
                if (t.getEnemy() != null) enemies++;
                if (t.getItem() != null) items++;
            }
        }

        // MapGenerator places: goblins=3, skeletons=2, orcs=1 -> 6 enemies
        // items: potions=3, weapons=1 -> 4 items
        assertEquals(6, enemies, "Expected exactly 6 enemies placed");
        assertEquals(4, items, "Expected exactly 4 items placed");
    }

    @Test
    void bordersAreWalls() {
        Player p = new Player("Tester", 20, 0, 0);
        Dungeon d = MapGenerator.createInitialWorld(10, 10, p, new Rng(7));

        int w = d.getWidth();
        int h = d.getHeight();

        // Top & bottom rows
        for (int x = 0; x < w; x++) {
            assertEquals(TileType.WALL, d.getTile(x, 0).getType(), "Top border must be WALL");
            assertEquals(TileType.WALL, d.getTile(x, h - 1).getType(), "Bottom border must be WALL");
        }
        // Left & right columns
        for (int y = 0; y < h; y++) {
            assertEquals(TileType.WALL, d.getTile(0, y).getType(), "Left border must be WALL");
            assertEquals(TileType.WALL, d.getTile(w - 1, y).getType(), "Right border must be WALL");
        }
    }
}
