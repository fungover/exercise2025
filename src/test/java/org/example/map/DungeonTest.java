package org.example.map;

import org.example.entities.Enemy;
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


    @Test
    void enemy_is_placed_on_map() {
        Dungeon dungeon = new Dungeon();

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
}
