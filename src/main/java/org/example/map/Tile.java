package org.example.map;

import org.example.entities.enemies.Enemy;
import org.example.entities.items.Item;
import java.util.ArrayList;
import java.util.List;

/** A single cell in the dungeon. Will extend later with items/enemies. */
public final class Tile {
    private TileType type;
    private final List<Item> items = new ArrayList<>();
    private Enemy enemy;

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public void addItem(Item item) {
        if (item != null) items.add(item);
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }

    public List<Item> items() {
        return List.copyOf(items);
    }

    public Item takeAt(int zeroBasedIndex) {
        if (zeroBasedIndex < 0 || zeroBasedIndex >= items.size()) return null;
        return items.remove(zeroBasedIndex);
    }

    public List<Item> takeAll() {
        List<Item> out = new ArrayList<>(items);
        items.clear();
        return out;
    }

    public boolean hasEnemy() {
        return enemy != null && !enemy.isDead();
    }

    public Enemy enemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void removeEnemy() {
        enemy = null;
    }
}
