package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import entities.Player;
import map.PirateCave;
import game.InputHandler.Direction;
import utils.Constants;

@DisplayName("Movement Service Tests")
class MovementServiceTest {

    private MovementService movementService;
    private Player player;
    private PirateCave cave;

    @BeforeEach
    void setUp() {
        movementService = new MovementService();
        player = new Player("Test Player", Constants.PLAYER_STARTING_HEALTH, Constants.PLAYER_STARTING_DAMAGE);
        cave = new PirateCave(3, 3); // Mindre karta för testing
    }

    @Test
    @DisplayName("Valid movement should succeed")
    void testValidMovement() {
        // Arrange
        player.setPosition(1, 1); // Mitt på kartan

        // Act
        MovementService.MovementResult result = movementService.movePlayer(player, cave, Direction.EAST);

        // Assert
        assertTrue(result.isSuccess(), "Movement should succeed");
        assertEquals(2, result.getFinalX(), "Player should move to x=2");
        assertEquals(1, result.getFinalY(), "Player should stay at y=1");
        assertTrue(result.getMessage().contains("österut"), "Message should indicate eastward movement");
    }

    @Test
    @DisplayName("Movement out of bounds should fail")
    void testMovementOutOfBounds() {
        // Arrange
        player.setPosition(0, 0); // Övre vänstra hörnet

        // Act
        MovementService.MovementResult result = movementService.movePlayer(player, cave, Direction.NORTH);

        // Assert
        assertFalse(result.isSuccess(), "Movement out of bounds should fail");
        assertEquals(0, result.getFinalX(), "Player should remain at x=0");
        assertEquals(0, result.getFinalY(), "Player should remain at y=0");
        assertTrue(result.getMessage().contains("blockerad"), "Message should indicate blocked path");
    }

    @Test
    @DisplayName("Can move check should work correctly")
    void testCanMove() {
        // Arrange
        player.setPosition(1, 1);

        // Act & Assert
        assertTrue(movementService.canMove(player, cave, Direction.EAST), "Should be able to move east");
        assertTrue(movementService.canMove(player, cave, Direction.WEST), "Should be able to move west");
        assertTrue(movementService.canMove(player, cave, Direction.NORTH), "Should be able to move north");
        assertTrue(movementService.canMove(player, cave, Direction.SOUTH), "Should be able to move south");
    }

    @Test
    @DisplayName("Get available directions should return correct list")
    void testGetAvailableDirections() {
        // Arrange
        player.setPosition(0, 0); // Övre vänstra hörnet

        // Act
        var directions = movementService.getAvailableDirections(player, cave);

        // Assert
        assertFalse(directions.isEmpty(), "Should have at least one available direction");
        assertTrue(directions.contains(Direction.EAST), "Should be able to move east from corner");
        assertTrue(directions.contains(Direction.SOUTH), "Should be able to move south from corner");
        assertFalse(directions.contains(Direction.NORTH), "Should not be able to move north from top");
        assertFalse(directions.contains(Direction.WEST), "Should not be able to move west from left edge");
    }

    @Test
    @DisplayName("Movement should update player position")
    void testPlayerPositionUpdate() {
        // Arrange
        int startX = 1, startY = 1;
        player.setPosition(startX, startY);

        // Act
        movementService.movePlayer(player, cave, Direction.EAST);

        // Assert
        assertEquals(startX + 1, player.getX(), "Player X position should be updated");
        assertEquals(startY, player.getY(), "Player Y position should remain same");
    }
}
