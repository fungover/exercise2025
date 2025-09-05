package org.example;

import org.example.entities.Goblin;
import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.entities.Character;
import org.example.entities.HealthPotion;


import org.example.entities.Player;
import org.example.map.DungeonGrid;
import org.example.service.Combat;
import org.example.utils.ItemOnMap;
import org.example.utils.RandomGeneration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

public class GameTests {
    @Test
    void playerCannotMoveThroughWalls() {
        DungeonGrid grid = new DungeonGrid(5, 5);
        Player player = new Player("Test", 100, 0, 0); // Starts in corner
        int oldX = player.getX();
        int oldY = player.getY();

        // Try to move through walls
        player.move(-1, 0, grid);

        assertEquals(oldX, player.getX());
        assertEquals(oldY, player.getY());

    }
    @Test
    void combatReducesEnemyHealth() {
        Player player = new Player("Test", 100, 1, 1);
        Enemy goblin = new Goblin(1, 1);
        int oldHP = goblin.getHealth();

        Combat.doAttack(player, goblin); // New method without input from CLI

        assertTrue(player.getHealth() > oldHP, "Player HP should increase after using potion");

    }
    @Test
    void randomGenerationPlacesOnValidTile() {
        DungeonGrid grid = new DungeonGrid(10, 10);
        Player player = new Player("Test", 100, 2, 2);
        List<Character> enemies = new ArrayList<>();
        List<ItemOnMap> items = new ArrayList<>();

        int[] pos = RandomGeneration.getRandomPosition(grid, player, enemies, items);

        // Check that position is within bounds of grid
        assertTrue(pos[0] >= 0 && pos[0] < grid.getWidth());
        assertTrue(pos[1] >= 0 && pos[1] < grid.getHeight());

        // Check that position is not a wall
        assertFalse(grid.getTiles(pos[0], pos[1]).isWall());
    }

    @Test
    void usingPotionIncreasesHealth() {
        Player player = new Player("Test", 50, 1, 1);
        player.addItem(new HealthPotion("Small Potion", 20));
        int oldHP = player.getHealth();

        player.useItem(0);

        assertTrue(player.getHealth() > oldHP, "Player HP should increase after using potion");
    }

}
