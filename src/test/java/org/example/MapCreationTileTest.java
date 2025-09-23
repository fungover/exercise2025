package org.example;

import org.example.entities.Enemy;
import org.example.entities.Inventory;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.map.MapCreation;
import org.example.map.Tile;
import org.example.map.TileEnum;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MapCreationTileTest {

    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("onEnter on empty tile gives correct message")
    void onEnterEmpty() {
        Player player = new Player("Magnus", 100, 100, 100);
        Tile tile = new Tile(TileEnum.EMPTY, null, null);

        tile.onEnter(player, tile);

        assertEquals(TileEnum.EMPTY, tile.getType());
        assertTrue(outContent.toString().contains("Found empty tile"));
    }

    @Test
    @DisplayName("onEnter on CHEST tile gives item, makes tile EMPTY and correct messages")
    void onEnterChest() {
        Player player = new Player("Magnus", 100, 100, 100);

        Inventory inventory = new Inventory();
        player.setInventory(inventory);
        Inventory inv = player.getInventory();
        //This is just to test how it works in the real game, it looks stupid calling inventory twice

        assertFalse(inv.hasItem("Health Potion"));

        Item potion = new Item("Health Potion", "health_potion");
        Tile tile = new Tile(TileEnum.CHEST, null, potion);

        tile.onEnter(player, tile);

        assertTrue(inv.hasItem("Health Potion"));
        assertEquals(TileEnum.EMPTY, tile.getType());

        String output = outContent.toString();
        assertTrue(output.contains("You found a chest and opened it."));
        assertTrue(output.contains("Inside the chest you found Health Potion"));
    }

    @Test
    @DisplayName("onEnter on enemy tile kills low health enemy and clears tile to empty and checks correct messages")
    void onEnterEnemyKill() {
        Player player = new Player("Magnus", 100, 100, 100);
        Enemy skeleton = new Enemy("Skeleton", 10, 5, 0, 0);
        Tile tile = new Tile(TileEnum.ENEMY, skeleton, null);

        System.setIn(new ByteArrayInputStream("attack\n".getBytes()));

        tile.onEnter(player, tile);

        assertEquals(TileEnum.EMPTY, tile.getType());
        assertEquals(100, player.getHealth());

        String out = outContent.toString();
        assertTrue(out.contains("You stepped up on a Skeleton"));
        assertTrue(out.contains("You killed the Skeleton"));
        assertTrue(out.contains("Battle is over! You won!"));
    }

    @Test
    @DisplayName("checks that tile[0][0] is always EMPTY tile")
    void checksThatSpawnTileIsEmpty() {
        MapCreation map = new MapCreation(10, 10);

        Tile spawn = map.getTile(0, 0);

        assertEquals(TileEnum.EMPTY, spawn.getType());
    }
}
