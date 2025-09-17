package map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;

import entities.Player;
import entities.Enemy;
import entities.Item;
import enemies.EnemyFactory;
import items.PirateTreasureFactory;
import utils.Constants;

@DisplayName("Map Generation Tests")
class MapGenerationTest {

    private PirateCave cave;
    private Player player;

    @BeforeEach
    void setUp() {
        cave = new PirateCave(5, 4);
        player = new Player("Test Player", Constants.PLAYER_STARTING_HEALTH, Constants.PLAYER_STARTING_DAMAGE);
    }

    @Test
    @DisplayName("Cave should be created with correct dimensions")
    void testCaveDimensions() {
        // Assert
        assertEquals(5, cave.getGridWidth(), "Cave width should be 5");
        assertEquals(4, cave.getGridHeight(), "Cave height should be 4");
        assertEquals(11, cave.getDisplayWidth(), "Display width should be 5*2+1 = 11");
        assertEquals(9, cave.getDisplayHeight(), "Display height should be 4*2+1 = 9");
    }

    @Test
    @DisplayName("All game positions should be valid within bounds")
    void testValidGamePositions() {
        // Act & Assert
        for (int x = 0; x < cave.getGridWidth(); x++) {
            for (int y = 0; y < cave.getGridHeight(); y++) {
                assertTrue(cave.isValidGamePosition(x, y),
                        "Position (" + x + ", " + y + ") should be valid");
            }
        }
    }

    @Test
    @DisplayName("Out of bounds positions should be invalid")
    void testInvalidGamePositions() {
        // Assert
        assertFalse(cave.isValidGamePosition(-1, 0), "Negative X should be invalid");
        assertFalse(cave.isValidGamePosition(0, -1), "Negative Y should be invalid");
        assertFalse(cave.isValidGamePosition(cave.getGridWidth(), 0), "X >= width should be invalid");
        assertFalse(cave.isValidGamePosition(0, cave.getGridHeight()), "Y >= height should be invalid");
    }

    @Test
    @DisplayName("Player should be able to move to all valid positions")
    void testPlayerMovementPositions() {
        // Act & Assert
        for (int x = 0; x < cave.getGridWidth(); x++) {
            for (int y = 0; y < cave.getGridHeight(); y++) {
                assertTrue(cave.canPlayerMoveTo(x, y),
                        "Player should be able to move to (" + x + ", " + y + ")");
            }
        }
    }

    @Test
    @DisplayName("Items can be placed at valid positions")
    void testItemPlacement() {
        // Arrange
        Item testItem = PirateTreasureFactory.createSmallGoldCoin();

        // Act & Assert
        assertTrue(cave.placeItem(testItem, 2, 2), "Should be able to place item at center");
        assertTrue(cave.hasItemAt(2, 2), "Should have item at placed position");
        assertEquals(testItem, cave.getItemAt(2, 2), "Should retrieve same item");
    }

    @Test
    @DisplayName("Items cannot be placed at invalid positions")
    void testInvalidItemPlacement() {
        // Arrange
        Item testItem = PirateTreasureFactory.createSmallGoldCoin();

        // Act & Assert
        assertFalse(cave.placeItem(testItem, -1, 0), "Should not place item at negative position");
        assertFalse(cave.placeItem(testItem, 10, 10), "Should not place item outside bounds");
    }

    @Test
    @DisplayName("Enemies can be placed at valid positions")
    void testEnemyPlacement() {
        // Arrange
        Enemy testEnemy = EnemyFactory.createSkeleton();

        // Act & Assert
        assertTrue(cave.placeEnemy(testEnemy, 1, 1), "Should be able to place enemy");
        assertTrue(cave.hasEnemyAt(1, 1), "Should have enemy at placed position");
        assertEquals(testEnemy, cave.getEnemyAt(1, 1), "Should retrieve same enemy");
    }

    @Test
    @DisplayName("Player cannot move to positions with enemies")
    void testEnemyBlocksMovement() {
        // Arrange
        Enemy testEnemy = EnemyFactory.createSkeleton();
        cave.placeEnemy(testEnemy, 2, 2);

        // Act & Assert
        assertFalse(cave.canPlayerMoveTo(2, 2), "Player should not be able to move to enemy position");
    }

    @Test
    @DisplayName("Items and enemies can be removed")
    void testItemAndEnemyRemoval() {
        // Arrange
        Item testItem = PirateTreasureFactory.createSmallGoldCoin();
        Enemy testEnemy = EnemyFactory.createSpider();
        cave.placeItem(testItem, 1, 1);
        cave.placeEnemy(testEnemy, 2, 2);

        // Act
        Item removedItem = cave.removeItemAt(1, 1);
        Enemy removedEnemy = cave.removeEnemyAt(2, 2);

        // Assert
        assertEquals(testItem, removedItem, "Should remove correct item");
        assertEquals(testEnemy, removedEnemy, "Should remove correct enemy");
        assertFalse(cave.hasItemAt(1, 1), "Should no longer have item");
        assertFalse(cave.hasEnemyAt(2, 2), "Should no longer have enemy");
    }

    @Test
    @DisplayName("Tiles should exist at all display positions")
    void testTileExistence() {
        // Act & Assert
        for (int x = 0; x < cave.getDisplayWidth(); x++) {
            for (int y = 0; y < cave.getDisplayHeight(); y++) {
                Tile tile = cave.getTile(x, y);
                assertNotNull(tile, "Tile should exist at display position (" + x + ", " + y + ")");
            }
        }
    }

    @RepeatedTest(5)
    @DisplayName("Random enemy population should work correctly")
    void testRandomEnemyPopulation() {
        // Act
        cave.populateWithEnemies(3);

        // Assert
        int enemyCount = 0;
        for (int x = 0; x < cave.getGridWidth(); x++) {
            for (int y = 0; y < cave.getGridHeight(); y++) {
                if (cave.hasEnemyAt(x, y)) {
                    enemyCount++;
                }
            }
        }

        assertTrue(enemyCount <= 3, "Should not have more enemies than requested");
        // Note: Might have fewer than 3 if placement fails due to collisions
    }

    @Test
    @DisplayName("Multiple items cannot occupy same position")
    void testSingleItemPerPosition() {
        // Arrange
        Item item1 = PirateTreasureFactory.createSmallGoldCoin();
        Item item2 = PirateTreasureFactory.createMagicKey();

        // Act
        boolean first = cave.placeItem(item1, 2, 2);
        boolean second = cave.placeItem(item2, 2, 2); // Same position

        // Assert
        assertTrue(first, "First item should be placed successfully");
        assertFalse(second, "Second item should not replace first item");
        assertEquals(item1, cave.getItemAt(2, 2), "Should still have first item");
    }

    @Test
    @DisplayName("Cave display should not crash")
    void testDisplayMapStability() {
        // Arrange - Add some content
        cave.placeItem(PirateTreasureFactory.createSmallGoldCoin(), 1, 1);
        cave.placeEnemy(EnemyFactory.createSkeleton(), 2, 2);

        // Act & Assert - Should not throw exceptions
        assertDoesNotThrow(() -> cave.displayMap(player), "Display with player should not crash");
        assertDoesNotThrow(() -> cave.displayMap(), "Display without player should not crash");
    }
}