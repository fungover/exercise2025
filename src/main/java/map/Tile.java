package map;

import entities.Enemy;
import entities.Item;
import utils.Position;

public class Tile {
    private final Position position;
    private TileType type;
    private Enemy enemy;
    private Item item;

    public Tile (int x, int y, TileType type) {
        this.position = new Position(x, y);
        this.type = type;
    }

    public Position getPosition() {
        return position;
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

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isWalkable() {
        return type == TileType.FLOOR;
    }

    public boolean isEmpty() {
        return isWalkable() && enemy == null && item == null;
    }

    @Override
    public String toString() {
        if (enemy != null) {
            return "E";
        }

        if (item != null)  {
            return "i";
        }

        return switch (type) {
            case WALL -> "#";
            case FLOOR -> ".";
            case DOOR -> "+";
        };
    }
}
