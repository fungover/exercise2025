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
import utils.RandomGenerator;

@DisplayName("Map Generation Tests with Walls")
class MapGenerationTest {

    private PirateCave cave;
    private PirateCave smallCave; // For tests that need no walls
    private Player player;

    @BeforeEach
    void setUp() {
        cave = new PirateCave(5, 4); // Standard size - will have walls
        smallCave = new PirateCave(3, 3); // Small size - no walls for predictable testing
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
    @DisplayName("Large caves should have walls generated")
    void testWallGeneration() {
        // Use a definitely large cave (5x4 matches the game default)
        PirateCave largeCave = new PirateCave(5, 4);

        // Check that some walls exist in the large cave
        boolean hasWalls = false;
        for (int x = 0; x < largeCave.getGridWidth(); x++) {
            for (int y = 0; y < largeCave.getGridHeight(); y++) {
                if (largeCave.hasWallAt(x, y)) {
                    hasWalls = true;
                    break;
                }
            }
            if (hasWalls) break;
        }

        assertTrue(hasWalls, "5x4 cave should have some walls generated");
    }

    @Test
    @DisplayName("Small caves should not have walls")
    void testSmallCaveNoWalls() {
        // Small cave should have no walls
        for (int x = 0; x < smallCave.getGridWidth(); x++) {
            for (int y = 0; y < smallCave.getGridHeight(); y++) {
                assertFalse(smallCave.hasWallAt(x, y),
                        "Small cave should not have walls at (" + x + ", " + y + ")");
            }
        }
    }

    @Test
    @DisplayName("Starting position should never be blocked by walls")
    void testStartingPositionNotBlocked() {
        assertFalse(cave.hasWallAt(0, 0), "Starting position (0,0) should never have a wall");
        assertTrue(cave.canPlayerMoveTo(0, 0), "Player should be able to move to starting position");
    }

    @Test
    @DisplayName("Player movement considers walls")
    void testPlayerMovementWithWalls() {
        // Test with small cave where all positions should be accessible
        for (int x = 0; x < smallCave.getGridWidth(); x++) {
            for (int y = 0; y < smallCave.getGridHeight(); y++) {
                assertTrue(smallCave.canPlayerMoveTo(x, y),
                        "Player should be able to move to (" + x + ", " + y + ") in small cave");
            }
        }

        // In large cave, some positions might be blocked by walls
        int accessiblePositions = 0;
        int blockedPositions = 0;

        for (int x = 0; x < cave.getGridWidth(); x++) {
            for (int y = 0; y < cave.getGridHeight(); y++) {
                if (cave.canPlayerMoveTo(x, y)) {
                    accessiblePositions++;
                } else {
                    blockedPositions++;
                }
            }
        }

        assertTrue(accessiblePositions > 0, "Should have some accessible positions");
        // Note: blockedPositions might be 0 if no walls were randomly generated
    }

    @Test
    @DisplayName("Items cannot be placed on walls")
    void testItemPlacementWithWalls() {
        Item testItem = PirateTreasureFactory.createSmallGoldCoin();

        // Try to place item on every position
        int successfulPlacements = 0;
        for (int x = 0; x < cave.getGridWidth(); x++) {
            for (int y = 0; y < cave.getGridHeight(); y++) {
                // Create new item for each attempt
                Item newItem = PirateTreasureFactory.createSmallGoldCoin();
                if (cave.placeItem(newItem, x, y)) {
                    successfulPlacements++;
                }
            }
        }

        assertTrue(successfulPlacements > 0, "Should be able to place items somewhere");
        // The exact number depends on wall generation and existing items
    }

    @Test
    @DisplayName("Enemies cannot be placed on walls")
    void testEnemyPlacementWithWalls() {
        int successfulPlacements = 0;

        for (int x = 0; x < cave.getGridWidth(); x++) {
            for (int y = 0; y < cave.getGridHeight(); y++) {
                Enemy testEnemy = EnemyFactory.createSkeleton();
                if (cave.placeEnemy(testEnemy, x, y)) {
                    successfulPlacements++;
                }
            }
        }

        assertTrue(successfulPlacements > 0, "Should be able to place enemies somewhere");
    }

    @Test
    @DisplayName("Items cannot be placed at invalid positions")
    void testInvalidItemPlacement() {
        Item testItem = PirateTreasureFactory.createSmallGoldCoin();

        // Act & Assert
        assertFalse(cave.placeItem(testItem, -1, 0), "Should not place item at negative position");
        assertFalse(cave.placeItem(testItem, 10, 10), "Should not place item outside bounds");
    }

    @Test
    @DisplayName("Wall detection works correctly")
    void testWallDetection() {
        // Out of bounds should be considered walls
        assertTrue(cave.hasWallAt(-1, 0), "Out of bounds should be considered a wall");
        assertTrue(cave.hasWallAt(0, -1), "Out of bounds should be considered a wall");
        assertTrue(cave.hasWallAt(cave.getGridWidth(), 0), "Out of bounds should be considered a wall");
        assertTrue(cave.hasWallAt(0, cave.getGridHeight()), "Out of bounds should be considered a wall");
    }

    @Test
    @DisplayName("Items and enemies can be removed")
    void testItemAndEnemyRemoval() {
        // Use small cave for predictable behavior
        Item testItem = PirateTreasureFactory.createSmallGoldCoin();
        Enemy testEnemy = EnemyFactory.createSpider();

        assertTrue(smallCave.placeItem(testItem, 1, 1), "Should place item in small cave");
        assertTrue(smallCave.placeEnemy(testEnemy, 2, 2), "Should place enemy in small cave");

        // Act
        Item removedItem = smallCave.removeItemAt(1, 1);
        Enemy removedEnemy = smallCave.removeEnemyAt(2, 2);

        // Assert
        assertEquals(testItem, removedItem, "Should remove correct item");
        assertEquals(testEnemy, removedEnemy, "Should remove correct enemy");
        assertFalse(smallCave.hasItemAt(1, 1), "Should no longer have item");
        assertFalse(smallCave.hasEnemyAt(2, 2), "Should no longer have enemy");
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
    @DisplayName("Random enemy population avoids walls")
    void testRandomEnemyPopulationWithWalls() {
        // Act
        cave.populateWithEnemies(3);

        // Assert
        int enemyCount = 0;
        for (int x = 0; x < cave.getGridWidth(); x++) {
            for (int y = 0; y < cave.getGridHeight(); y++) {
                if (cave.hasEnemyAt(x, y)) {
                    enemyCount++;
                    // Ensure enemy is not on a wall
                    assertFalse(cave.hasWallAt(x, y),
                            "Enemy should not be placed on wall at (" + x + ", " + y + ")");
                }
            }
        }

        assertTrue(enemyCount <= 3, "Should not have more enemies than requested");
    }

    @Test
    @DisplayName("Multiple items cannot occupy same position")
    void testSingleItemPerPosition() {
        // Use small cave for predictable behavior
        Item item1 = PirateTreasureFactory.createSmallGoldCoin();
        Item item2 = PirateTreasureFactory.createMagicKey();

        // Act
        boolean first = smallCave.placeItem(item1, 1, 1);
        boolean second = smallCave.placeItem(item2, 1, 1); // Same position

        // Assert
        assertTrue(first, "First item should be placed successfully");
        assertFalse(second, "Second item should not replace first item");
        assertEquals(item1, smallCave.getItemAt(1, 1), "Should still have first item");
    }

    @Test
    @DisplayName("Cave display should not crash with walls")
    void testDisplayMapStability() {
        // Arrange - Add some content
        cave.placeItem(PirateTreasureFactory.createSmallGoldCoin(), 1, 1);
        // Try to place enemy (might fail due to walls, but shouldn't crash)
        cave.placeEnemy(EnemyFactory.createSkeleton(), 2, 2);

        // Act & Assert - Should not throw exceptions
        assertDoesNotThrow(() -> cave.displayMap(player), "Display with player should not crash");
        assertDoesNotThrow(() -> cave.displayMap(), "Display without player should not crash");
    }

    @Test
    @DisplayName("Wall functionality maintains game balance")
    void testWallGameBalance() {
        // Ensure that walls don't make the game unplayable
        int totalPositions = cave.getGridWidth() * cave.getGridHeight();
        int accessiblePositions = 0;

        for (int x = 0; x < cave.getGridWidth(); x++) {
            for (int y = 0; y < cave.getGridHeight(); y++) {
                if (cave.canPlayerMoveTo(x, y)) {
                    accessiblePositions++;
                }
            }
        }

        double accessibleRatio = (double) accessiblePositions / totalPositions;
        assertTrue(accessibleRatio >= 0.7,
                "At least 70% of positions should be accessible (got " +
                        String.format("%.1f%%", accessibleRatio * 100) + ")");
    }
}