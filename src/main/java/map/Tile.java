package map;

import entities.Enemy;
import entities.Item;

/**
 * En ruta i kartan. Kan innehålla en fiende eller ett föremål.
 */
public class Tile {
    private String type;
    private Enemy enemy;
    private Item item;

    public Tile(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        if (enemy != null) {
            this.type = TileType.ENEMY;
        }
    }

    public void removeEnemy() {
        this.enemy = null;
        if (this.type.equals(TileType.ENEMY)) {
            this.type = TileType.EMPTY;
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        if (item != null) {
            this.type = TileType.ITEM;
        }
    }

    public Item pickUpItem() {
        Item picked = this.item;
        this.item = null;
        if (this.type.equals(TileType.ITEM)) {
            this.type = TileType.EMPTY;
        }
        return picked;
    }

    public boolean isBlocking() {
        return this.type.equals(TileType.WALL);
    }
}
