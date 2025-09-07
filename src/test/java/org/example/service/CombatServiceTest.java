package org.example.service;

import org.example.map.Dungeon;
import org.example.map.BasicMapGenerator;
import org.example.entities.Player;
import org.example.entities.Enemy;
import org.example.entities.enemies.Troll;
import org.example.entities.behaviors.BasicCombat;
import org.example.utils.InputValidator;
import org.example.entities.Tile;
import org.example.entities.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombatServiceTest {
    private Dungeon dungeon;
    private CombatService combatService;
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Hero", 100, 10, new BasicCombat(10));
        dungeon = new Dungeon(5, 5, player, new BasicMapGenerator() {
            @Override
            public Tile[][] generate(int width, int height) {
                Tile[][] tiles = new Tile[height][width];
                // Initialize all tiles as EMPTY
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        tiles[y][x] = new Tile(TileType.EMPTY);
                    }
                }
                // Add walls around border
                for (int x = 0; x < width; x++) {
                    tiles[0][x] = new Tile(TileType.WALL);
                    tiles[height - 1][x] = new Tile(TileType.WALL);
                }
                for (int y = 0; y < height; y++) {
                    tiles[y][0] = new Tile(TileType.WALL);
                    tiles[y][width - 1] = new Tile(TileType.WALL);
                }
                // Place Troll at (2, 2)
                tiles[2][2] = new Tile(new Troll());
                // Ensure (3, 3) is empty
                tiles[3][3] = new Tile(TileType.EMPTY);
                return tiles;
            }
        });
        combatService = new CombatService(dungeon, new InputValidator(dungeon));
    }

    @Test
    void testEngageCombat_ValidEnemy_DefeatsEnemy() {
        Tile tile = dungeon.getTile(2, 2);
        Enemy enemy = tile.getEnemy();
        assertNotNull(enemy, "Enemy should exist at (2, 2)");
        assertTrue(enemy.isAlive(), "Enemy should be alive");

        combatService.engageCombat(2, 2, player);

        assertFalse(enemy.isAlive(), "Enemy should be defeated");
        assertEquals(TileType.EMPTY, tile.getType(), "Tile should be cleared");
        assertNull(tile.getEnemy(), "Enemy should be null after defeat");
    }

    @Test
    void testEngageCombat_NonEnemyTile_NoAction() {
        Tile tile = dungeon.getTile(3, 3); // Empty tile, adjacent to (2, 3)
        combatService.engageCombat(3, 3, player);
        assertEquals(TileType.EMPTY, tile.getType(), "Tile should remain empty");
    }

    @Test
    void testEngageCombat_OutOfBounds_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> combatService.engageCombat(10, 10, player),
                "Should throw exception for out-of-bounds combat"
        );
    }

    @Test
    void testEngageCombat_NullPlayer_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> combatService.engageCombat(2, 2, null),
                "Should throw exception for null player"
        );
        assertEquals("Player cannot be null", exception.getMessage());
    }
}