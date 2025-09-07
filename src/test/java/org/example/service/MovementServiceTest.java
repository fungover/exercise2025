package org.example.service;

import org.example.map.Dungeon;
import org.example.map.BasicMapGenerator;
import org.example.entities.Player;
import org.example.entities.behaviors.BasicCombat;
import org.example.utils.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovementServiceTest {
    private Dungeon dungeon;
    private MovementService movementService;
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Hero", 100, 10, new BasicCombat(10));
        dungeon = new Dungeon(5, 5, player, new BasicMapGenerator());
        movementService = new MovementService(dungeon, new InputValidator(dungeon));
    }

    @Test
    void testMovePlayer_ValidMove_UpdatesPosition() {
        int initialX = player.getX();
        int initialY = player.getY();
        movementService.movePlayer(1, 0, player); // Move right
        assertEquals(initialX + 1, player.getX(), "Player should move right");
        assertEquals(initialY, player.getY(), "Y position should not change");
    }

    @Test
    void testMovePlayer_IntoWall_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> movementService.movePlayer(0, -10, player),
                "Should throw exception for wall collision"
        );
        assertTrue(exception.getMessage().contains("Invalid move"));
    }

    @Test
    void testMovePlayer_OutOfBounds_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> movementService.movePlayer(10, 0, player),
                "Should throw exception for out-of-bounds move"
        );
    }

    @Test
    void testMovePlayer_NullPlayer_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> movementService.movePlayer(1, 0, null),
                "Should throw exception for null player"
        );
        assertEquals("Player cannot be null", exception.getMessage());
    }
}