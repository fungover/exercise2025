package org.example.service;

import org.example.entities.Item;
import org.example.entities.Position;
import org.example.utils.GameUtils;
import org.example.map.Dungeon;

import java.util.Random;

public class ItemSpawner {
    private final Dungeon dungeon;
    private final Random random;

    public ItemSpawner(Dungeon dungeon, Random random) {
        this.dungeon = dungeon;
        this.random = random;
    }

    public void spawnItem(int amount) {
        for (int i = 0; i < amount; i++) {
            Position pos = GameUtils.randomFloorPosition(dungeon, random);
            dungeon.getTile(pos).setItem(Item.getRandomItem());
        }
    }
}