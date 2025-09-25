package org.example.service;

import org.example.entities.Item;
import org.example.entities.Position;
import org.example.map.Dungeon;

import java.util.*;

public class ItemSpawner {
    private final Dungeon dungeon;
    private final Random random;

    public ItemSpawner(Dungeon dungeon, Random random) {
        this.dungeon = Objects.requireNonNull(dungeon, "dungeon");
        this.random = Objects.requireNonNull(random, "random");
    }

    public void spawnItem(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
        List<Position> free = new ArrayList<>();
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                Position p = new Position(x, y);
                var tile = dungeon.getTile(p);
                if (tile.isWalkable() && tile.getEnemy() == null && tile.getItem() == null) {
                    free.add(p);
                }
            }
        }
        if (free.isEmpty()) {
            return;
        }
        Collections.shuffle(free, random);
        int toPlace = Math.min(amount, free.size());
        for (int i = 0; i < toPlace; i++) {
            dungeon.getTile(free.get(i)).setItem(Item.getRandomItem());
        }
    }
}