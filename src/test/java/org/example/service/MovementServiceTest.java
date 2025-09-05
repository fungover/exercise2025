package org.example.service;

import org.example.entities.HealingMilk;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.map.FarmageddonMap;
import org.example.map.Tile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovementServiceTest {

    private FarmageddonMap createEmptyMap(int width, int height) {
        FarmageddonMap map = new FarmageddonMap(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map.getTile(x, y).setType(Tile.Type.PATH);
            }
        }
        return map;
    }

    @Test
    void playerCannotMoveIntoWall() {
        FarmageddonMap map = createEmptyMap(3, 3);
        map.getTile(1, 0).setType(Tile.Type.WALL); //Wall in front of player

        Player player = new Player("Test", 100, 0, 0);
        MovementService movementService = new MovementService();

        movementService.move(player, map, 1, 0); // try to move into a wall

        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void playerCannotMoveOutsideMap() {
        FarmageddonMap map = createEmptyMap(3, 3);
        Player player = new Player("Test", 100, 0, 0);
        MovementService movementService = new MovementService();

        movementService.move(player, map, -1, 0); // try to move outside map

        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void playerPicksUpItem() {
        FarmageddonMap map = createEmptyMap(3, 3);
        Item milk = new HealingMilk(1, 0);
        map.getTile(1, 0).setType(Tile.Type.ITEM);
        map.getTile(1, 0).setItem(milk);

        Player player = new Player("Test", 100, 0, 0);
        MovementService movementService = new MovementService();

        movementService.move(player, map, 1, 0); // go to item

        List<Item> inventory = player.getInventory();
        assertTrue(inventory.contains(milk));
        assertNull(map.getTile(1, 0).getItem()); // item should be removed from map
    }

    @Test
    void playerPositionUnchangedOnInvalidMove() {
        FarmageddonMap map = createEmptyMap(3, 3);
        map.getTile(0, 1).setType(Tile.Type.WALL); // wall south

        Player player = new Player("Test", 100, 0, 0);
        MovementService movementService = new MovementService();

        movementService.move(player, map, 0, 1); // try to move south

        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }
}

