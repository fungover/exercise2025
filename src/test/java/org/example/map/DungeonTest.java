package org.example.map;

import org.example.entities.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DungeonTest {

    @Test
    void border_is_wall_and_start_walkable() {
        Dungeon dungeon = new Dungeon(2);

        // Hörn = WALL
        assertEquals(TileType.WALL, dungeon.get(0,0).getType());
        assertEquals(TileType.WALL, dungeon.get(0,9).getType());
        assertEquals(TileType.WALL, dungeon.get(9,0).getType());
        assertEquals(TileType.WALL, dungeon.get(9,9).getType());

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

    @Test
    void floor1_has_stairs_and_floor2_has_exit() {
        Dungeon floor1 = new Dungeon(1);
        Dungeon floor2 = new Dungeon(2);

        // På floor 1 ska rutan (8,8) vara STAIRS
        assertEquals(TileType.STAIRS, floor1.get(8,8).getType(),
                "Floor 1 should have stairs at (8,8)");

        // På floor 2 ska rutan (8,8) vara EXIT
        assertEquals(TileType.EXIT, floor2.get(8,8).getType(),
                "Floor 2 should have exit at (8,8)");
    }

    @Test
    void stairsShouldBeBlockedByEnemyUntilDefeated() {
        Dungeon dungeon = new Dungeon(1); // floor 1 -> har stairs
        Player player = new Player("Test");

        // Hitta stairs
        Tile stairs = null;
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                if (dungeon.get(x, y).getType() == TileType.STAIRS) {
                    stairs = dungeon.get(x, y);
                    break;
                }
            }
        }
        assertNotNull(stairs, "Stairs should exist on floor 1");

        // Kolla att stairs har en miniboss ('Dragon')
        assertTrue(stairs.hasEnemy(), "Stairs should be blocked by an enemy");
        assertTrue(stairs.getEnemy() instanceof Dragon, "Enemy on stairs should be a Dragon");

        // När fienden besegras → stairs ska inte längre ha enemy
        stairs.removeEnemy();
        assertFalse(stairs.hasEnemy(), "Stairs should be free after enemy is defeated");
    }

    @Test
    void exitShouldBeBlockedByBossUntilDefeated() {
        Dungeon dungeon = new Dungeon(2); // Floor 2 -> har exit
        Player player = new Player("Test");

        // Hitta exit
        Tile exit = null;
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                if (dungeon.get(x, y).getType() == TileType.EXIT) {
                    exit = dungeon.get(x, y);
                    break;
                }
            }
        }
        assertNotNull(exit, "Exit should exist on final floor");

        // Kolla att exit har en boss (Dragon)
        assertTrue(exit.hasEnemy(), "Exit should be blocked by a boss");
        assertTrue(exit.getEnemy() instanceof FinalBoss, "Enemy on exit should be a Balrog");

        // När bossen besegras → exit ska vara fri
        exit.removeEnemy();
        assertFalse(exit.hasEnemy(), "Exit should be free after boss is defeated");
    }
}
