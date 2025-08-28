package org.example.service;

import org.example.entities.HealthPotion;
import org.example.entities.Item;
import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class ItemService {

    private final List<Item> items;

    public ItemService() {
        this.items = new ArrayList<>();
    }

    public void placeRandomItems(Dungeon dungeon) { // Method to place random items in the dungeon
        List<Position> positions = RandomGenerator.getRandomFloorPositions(3, dungeon); // Calls random generator to get 3 random floor positions

        for (Position pos : positions) { // For each position, create a health potion and add it to the items list
            HealthPotion potion = new HealthPotion(pos);
            items.add(potion);
        }
    }

    public Item getItemAt(Position position) { // Method to get an item at a specific position
        return items.stream() // Create a stream from the items list
                .filter(item -> item.getPosition().equals(position)) // Filter items to find those that match the position
                .findFirst() // Find the first item that matches the position
                .orElse(null); // Return the item if found, otherwise return null
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
