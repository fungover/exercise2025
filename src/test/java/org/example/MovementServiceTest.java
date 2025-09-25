package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.service.Direction;
import org.example.service.MovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementServiceTest {

    private MovementService movementService;
    private Player player;
    private Dungeon dungeon;

    @BeforeEach
    void setUp() {
        movementService = new MovementService(); //Before every test, create a new instance of MovementService
        player = new Player("TestPlayer", 100, new Position(1, 1)); // Creates a player at position (1,1)
        dungeon = new Dungeon(8, 10); // Creates same dungeon as in RoomService
    }


    // TEST 1: Testing movement that will succeed by going east from (1,1) to (2,1).
    @Test
    void testValidMovement() {
        Position originalPos = player.getPosition(); //Players original position (1,1).
        boolean moved = movementService.movePlayer(player, Direction.EAST, dungeon); //Try to move east (2,1).

        assertTrue(moved); //Movement should succeed.
        assertEquals(new Position(2, 1), player.getPosition()); //Player's new position should be (2,1).
        assertNotEquals(originalPos, player.getPosition()); //Player's position should have changed.
    }

    // TEST 2: Testing movement that will fail by going north from (1,1) into a wall.
    @Test
    void testMovementIntoWall() {
        player.setPosition(new Position(1, 1)); // Ensure player starts at (1,1)
        Position originalPos = player.getPosition(); // Original position (1,1).

        boolean moved = movementService.movePlayer(player, Direction.NORTH, dungeon); // Try walk north into wall.

        assertFalse(moved); // Movement should fail (return false).
        assertEquals(originalPos, player.getPosition()); // Player's position should remain unchanged at (1,1).
    }

    // TEST 3: Testing movement in all four directions from (6,2) to ensure correct position updates.
    @Test
    void testAllDirection() {
        player.setPosition(new Position(6, 2)); //Ensure player starts at (6,2)

        assertTrue(movementService.movePlayer(player, Direction.NORTH, dungeon)); //Move north to (6,1)
        assertEquals(new Position(6, 1), player.getPosition());

        assertTrue(movementService.movePlayer(player, Direction.SOUTH, dungeon)); //Move south back to (6,2)
        assertEquals(new Position(6, 2), player.getPosition());

        assertTrue(movementService.movePlayer(player, Direction.WEST, dungeon)); //Move west to (5,2)
        assertEquals(new Position(5, 2), player.getPosition());

        assertTrue(movementService.movePlayer(player, Direction.EAST, dungeon)); //Move east back to (6,2)
        assertEquals(new Position(6, 2), player.getPosition());
    }
}
