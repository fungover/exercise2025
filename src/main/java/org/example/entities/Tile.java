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

    public TileType getType() {
        return type;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Item getItem() {
        return item;
    }

    public boolean isPassable() {
        return type != TileType.WALL;
    }

    public void setEnemy(Enemy enemy) {
        if (enemy == null) {
            this.enemy = null;
            if (this.item == null) {
                this.type = TileType.EMPTY;
            }
        } else {
            this.enemy = enemy;
            this.item = null;
            this.type = TileType.ENEMY;
        }
    }

    public void setItem(Item item) {
        if (item == null) {
            this.item = null;
            if (this.enemy == null) {
                this.type = TileType.EMPTY;
            }
        } else {
            this.item = item;
            this.enemy = null;
            this.type = TileType.ITEM;
        }
    }

    public void clearContents() {
        this.enemy = null;
        this.item = null;
        this.type = TileType.EMPTY;
    }
}

