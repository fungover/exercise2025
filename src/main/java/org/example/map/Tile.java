package org.example.map;

import org.example.entities.enemies.Enemy;
import org.example.entities.items.Item;

//en ruta i dungeon-kartan, som vägg, fiende eller item. elr tomt
public class Tile {
private String tileType;
private int row;
private int col;
private Enemy enemy;
private Item item;

public Tile(String tileType) {
    this.tileType = tileType;
    this.row = row;
    this.col = col;
}

    public boolean isWalkable() {
        return true;
    }

public String getTileType() {
    return tileType;
}

public Enemy getEnemy() {
    return enemy;
}

public void setEnemy(Enemy enemy) {
    this.enemy = enemy;
}

public Item getItem() {
    return item;
}

public void setItem(Item item) {
    this.item = item;
}


    public int getRow() {
        return row;
    }

    public int getCol() {
    return col;
    }
}
