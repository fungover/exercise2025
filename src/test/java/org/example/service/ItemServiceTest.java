package org.example.service;

import org.example.map.Dungeon;
import org.example.map.BasicMapGenerator;
import org.example.entities.Player;
import org.example.entities.items.Potion;
import org.example.entities.behaviors.BasicCombat;
import org.example.utils.InputValidator;
import org.example.entities.Tile;
import org.example.entities.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemServiceTest {
    private Dungeon dungeon;
    private ItemService itemService;
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
                // Place Potion at (2, 2)
                tiles[2][2] = new Tile(new Potion("Health Potion", 20));
                // Ensure (3, 3) is empty
                tiles[3][3] = new Tile(TileType.EMPTY);
                return tiles;
            }
        });
        itemService = new ItemService(dungeon, new InputValidator(dungeon));
    }

    @Test
    void testPickUpItem_ValidItem_AddsToInventory() {
        Tile tile = dungeon.getTile(2, 2);
        assertEquals(TileType.ITEM, tile.getType(), "Tile should contain an item");
        assertNotNull(tile.getItem(), "Item should exist at (2, 2)");

        itemService.pickUpItem(2, 2, player);

        assertEquals(1, player.getInventory().size(), "Player should have one item");
        assertEquals("Health Potion", player.getInventory().get(0).getName(), "Item should be Health Potion");
        assertEquals(TileType.EMPTY, tile.getType(), "Tile should be cleared");
        assertNull(tile.getItem(), "Item should be null after pickup");
    }

    @Test
    void testPickUpItem_NonItemTile_NoAction() {
        Tile tile = dungeon.getTile(3, 3); // Empty tile
        itemService.pickUpItem(3, 3, player);
        assertEquals(0, player.getInventory().size(), "Inventory should remain empty");
        assertEquals(TileType.EMPTY, tile.getType(), "Tile should remain empty");
    }

    @Test
    void testPickUpItem_OutOfBounds_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> itemService.pickUpItem(10, 10, player),
                "Should throw exception for out-of-bounds pickup"
        );
    }

    @Test
    void testPickUpItem_NullPlayer_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> itemService.pickUpItem(2, 2, null),
                "Should throw exception for null player"
        );
        assertEquals("Player cannot be null", exception.getMessage());
    }
}