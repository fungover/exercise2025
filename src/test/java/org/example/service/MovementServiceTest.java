package org.example.service;

import org.example.entities.characters.Player;
import org.example.entities.items.Weapon;
import org.example.map.DungeonGrid;
import org.example.map.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovementServiceTest {
    private MovementService movementService;
    private Player player;
    private DungeonGrid grid;
    private Weapon testWeapon;

    @BeforeEach
    @DisplayName("Set up")
    void setUp() {
        movementService = new MovementService();
        testWeapon = new Weapon("Test Sword", "A test weapon", 10, 15);
        player = new Player("TestPlayer", 100, 100, 2, 2, testWeapon);
        grid = DungeonGrid.createDungeonGrid(10, 10);

        grid.getTiles()[2][2].setType(Tile.TileType.FLOOR);
        grid.getTiles()[2][2].setHasPlayer(true);
    }

    @Test
    @DisplayName("Test valid move to floor")
    void testMovePlayerOntoFloor() {
        grid.getTiles()[3][2].setType(Tile.TileType.FLOOR);

        boolean gameOver = movementService.movePlayer(player, grid, 1, 0);

        assertFalse(gameOver);
        assertEquals(3, player.getX());
        assertEquals(2, player.getY());
        assertTrue(grid.getTiles()[3][2].getHasPlayer());
        assertFalse(grid.getTiles()[2][2].getHasPlayer());
    }

    @Test
    @DisplayName("Test invalid move to wall")
    void testMovePlayerOntoWall() {
        grid.getTiles()[3][2].setType(Tile.TileType.WALL);

        boolean gameOver = movementService.movePlayer(player, grid, 1, 0);

        assertFalse(gameOver);
        assertEquals(2, player.getX());
        assertEquals(2, player.getY());
        assertFalse(grid.getTiles()[3][2].getHasPlayer());
        assertTrue(grid.getTiles()[2][2].getHasPlayer());

    }

    @Test
    @DisplayName("Game over when entering a door")
    void testGameOverWhenEnteringDoor() {
        grid.getTiles()[3][2].setType(Tile.TileType.DOOR);

        assertTrue(movementService.movePlayer(player, grid, 1, 0));
    }

    @Test
    @DisplayName("Game over when entering an exit")
    void testGameOverWhenEnteringExit() {
        grid.getTiles()[3][2].setType(Tile.TileType.EXIT);

        assertTrue(movementService.movePlayer(player, grid, 1, 0));
    }

}
