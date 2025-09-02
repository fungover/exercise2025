package org.example.entities;

public class Tile {

    private TileType type;
    private Enemy enemy;
    private Item item;

    public Tile(TileType type) {
        if (type == null) {
            throw new IllegalArgumentException("Tile type cannot be null");
        }
        this.type = type;
        this.enemy = null;
        this.item = null;

    }

    public Tile(Enemy enemy) {
        if (enemy == null) {
            throw new IllegalArgumentException("Enemy cannot be null");
        }
        this.type = TileType.ENEMY;
        this.enemy = enemy;
        this.item = null;
    }

    public Tile(Item item) {
        if (item == null) {
        throw new IllegalArgumentException("Item cannot be null");
        }
        this.type = TileType.ITEM;
        this.enemy = null;
        this.item = item;
    }

    // Get the tiles

}

