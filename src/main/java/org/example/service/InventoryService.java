package org.example.service;

import org.example.entities.Item;
import org.example.entities.Player;

public class InventoryService {

    public void addItem(Player player, Item item) {
        player.pickUpItem(item);
        System.out.println(item.getName() + " added to inventory.");
    }
    public void useItem(Player player, int index) {
        player.useItem(index);
    }
}
