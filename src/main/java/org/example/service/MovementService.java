package org.example.service;

import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Position;
import org.example.entities.Character;
import org.example.map.Dungeon;
import org.example.map.Tile;

public class MovementService {

    private Dungeon dungeon;

    public MovementService(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public void move(Character character, Position newPosition) {
        character.setPosition(newPosition);
        System.out.println(character.getName() + " moves to " + newPosition);

        Tile tile = dungeon.getTile(newPosition);
        if (tile != null && tile.hasItem() && character instanceof Player player) {
            Item item = tile.getItem();
            player.getInventory().addItem(item);
            System.out.println(player.getName() + " picks up " + item.getName() + "!");
            tile.setItem(null);

        }
    }
}
