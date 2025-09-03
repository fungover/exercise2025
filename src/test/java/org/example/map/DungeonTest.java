package org.example.map;

import org.example.entities.Enemy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DungeonTest {

    @Test
    void border_is_wall_and_exit_is_correct_and_start_walkable() {
        Dungeon dungeon = new Dungeon(1);

        // Hörn = WALL
        assertEquals(TileType.WALL, dungeon.get(0,0).getType());
        assertEquals(TileType.WALL, dungeon.get(0,9).getType());
        assertEquals(TileType.WALL, dungeon.get(9,0).getType());
        assertEquals(TileType.WALL, dungeon.get(9,9).getType());

        // Exit på (8,8)
        assertEquals(TileType.EXIT, dungeon.get(8,8).getType());

        // Start (1,1) ska vara innanför kartan och inte WALL
        assertTrue(dungeon.inBounds(1,1));
        assertNotEquals(TileType.WALL, dungeon.get(1,1).getType());
    }

    @Test
    void inBounds_works() {
        Dungeon dungeon = new Dungeon(1);
        assertTrue(dungeon.inBounds(0, 0));
        assertTrue(dungeon.inBounds(9, 9));
        assertFalse(dungeon.inBounds(-1, 0));
        assertFalse(dungeon.inBounds(10, 0));
        assertFalse(dungeon.inBounds(0, -1));
        assertFalse(dungeon.inBounds(0, 10));
    }


    @Test
    void enemy_is_placed_on_map() {
        Dungeon dungeon = new Dungeon(1);

        // Leta efter minst en ruta med en fiende
        boolean foundEnemy = false;
        Enemy found = null;

        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                if (dungeon.get(x, y).hasEnemy()) {
                    foundEnemy = true;
                    found = dungeon.get(x, y).getEnemy();
                    break;
                }
            }
        }

        assertTrue(foundEnemy, "At least one enemy should be placed on the map");
        assertNotNull(found, "Found enemy should not be null");
        assertNotNull(found.getName(), "The enemy should have a name");
        assertTrue(found.getHp() > 0, "The enemy should have health");
    }
    @Test
    void items_are_placed_on_walkable_tiles() {
        Dungeon dungeon = new Dungeon(1);

        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                if (dungeon.get(x, y).hasItem()) {
                    assertNotEquals(TileType.WALL, dungeon.get(x, y).getType(),
                            "Items should not spawn on walls");
                }
            }
        }
    }

    @Test
    void different_dungeons_generate_different_layouts() {
        Dungeon dungeon1 = new Dungeon(1);
        Dungeon dungeon2 = new Dungeon(1);

        boolean differenceFound = false;

        for (int y = 0; y < dungeon1.getHeight(); y++) {
            for (int x = 0; x < dungeon1.getWidth(); x++) {
                boolean hasEnemy1 = dungeon1.get(x, y).hasEnemy();
                boolean hasEnemy2 = dungeon2.get(x, y).hasEnemy();
                if (hasEnemy1 != hasEnemy2) {
                    differenceFound = true;
                    break;
                }
            }
        }

        assertTrue(differenceFound, "Two dungeons of the same floor should differ due to randomness");
    }
}
