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

    public void placeRandomItems(Dungeon dungeon) {
        List<Position> positions = RandomGenerator.getRandomFloorPositions(3, dungeon);

        for (Position pos : positions) {
            HealthPotion potion = new HealthPotion(pos);
            items.add(potion);
        }
    }

    public Item getItemAt(Position position) {
        return items.stream()
                .filter(item -> item.getPosition().equals(position))
                .findFirst()
                .orElse(null);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
