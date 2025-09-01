package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.service.Direction;
import org.example.service.MovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovementServiceTest {

    private MovementService movementService;
    private Player player;
    private Dungeon dungeon;

    @BeforeEach
    public void setUp() {
        movementService = new MovementService();
        player = new Player("TestPlayer", 100, new Position(1,1));
    }

    @Test
    void testValidMovement() {

        Position originalPos = player.getPosition();

        boolean moved = movementService.movePlayer(player, Direction.EAST, dungeon);

        assertTrue(moved);
        assertEquals(new Position(2, 1), player.getPosition());
        assertNotEquals(originalPos, player.getPosition())
    }
}
