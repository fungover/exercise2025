package org.example;

import org.example.entities.characters.Player;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.entities.items.Weapon;
import org.example.entities.items.potions.HealthPotion;
import org.example.map.DungeonGrid;
import org.example.map.Tile;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Item> starterItems = new ArrayList<>();
        starterItems.add(new HealthPotion("Minor Health Potion", "Restores health", 10, 20));
        starterItems.add(new Weapon("The Spork of Indecision", "It can't decide if it wants to stab or scoop, so it does a little of both, poorly.", 3, 20));

        Inventory inventory = new Inventory(starterItems);

    }
}
