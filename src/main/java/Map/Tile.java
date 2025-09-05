package Map;

import Enemy.Enemy;
import Entities.Item;

public class Tile {
    private TileType type;
    private Enemy enemy;
    private Item item;

    public Tile(TileType type) { this.type = type; }

    public TileType getType() { return type; }
    public void setType(TileType type) { this.type = type; }

    public boolean isWalkable() { return type.isWalkable(); }

    public Enemy getEnemy() { return enemy; }
    public void setEnemy(Enemy enemy) { this.enemy = enemy; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    public boolean isEmptyContent() { return enemy == null && item == null; }
    public void clearContent() { enemy = null; item = null; }
}
