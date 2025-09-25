package org.example.service;

import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Tile;
import org.example.entities.TileType;
import org.example.map.Dungeon;
import org.example.utils.InputValidator;

public class ItemService {
    private final Dungeon dungeon;
    private final InputValidator validator;

    public ItemService(Dungeon dungeon, InputValidator validator) {
        if (dungeon == null) {
            throw new IllegalArgumentException("Dungeon cannot be null");
        }
        if (validator == null) {
            throw new IllegalArgumentException("Input validator cannot be null");
        }
        this.dungeon = dungeon;
        this.validator = validator;
        System.out.println("ItemService initialized for dungeon with dimensions " + dungeon.getWidth() + "x" + dungeon.getHeight());
    }

    public void pickUpItem(int x, int y, Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        System.out.println("Attempting to pick up item at " + x + "," + y + " from player at " + player.getX() + "," + player.getY());
        if (!validator.isValidInteraction(x, y, player)) {
            throw new IllegalArgumentException("Invalid interaction");
        }
        Tile tile = dungeon.getTile(x, y);
        System.out.println("Checking tile at (" + x + ", " + y + "): " + tile);
        if (tile.getType() != TileType.ITEM) {
            System.out.println("No item at ("+ x + ", " + y + ")");
            return;
        }
        Item item = tile.getItem();
        if (item == null) {
            System.out.println("No valid item at ("+ x + ", " + y + ") " + tile);
            return;
        }
        System.out.println("Picking up item: " + item);
        player.addItem(item);
        tile.clearContents();
    }
}
