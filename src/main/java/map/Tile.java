package map;

import entities.Item;

public class Tile {
    private char symbol;
    private String description;
    private Item item; // Föremål på denna tile (null om inget)

    public Tile(char symbol, String description) {
        this.symbol = symbol;
        this.description = description;
        this.item = null;
    }

    public void placeItem(Item item) {
        this.item = item;
    }

    public Item removeItem() {
        Item removedItem = this.item;
        this.item = null;
        return removedItem;
    }

    public boolean hasItem() {
        return item != null;
    }

    public Item getItem() {
        return item;
    }

    public char getDisplaySymbol() {
        if (hasItem()) {
            return item.getSymbol();
        }
        return symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getFullDescription() {
        String desc = description;
        if (hasItem()) {
            desc += " Du ser " + item.getName() + " här.";
        }
        return desc;
    }

    public String getDescription() {
        return description;
    }

    public boolean canWalkOn() {
        return symbol == ' ';
    }

    @Override
    public String toString() {
        String itemInfo = hasItem() ? " [" + item.getName() + "]" : "";
        return "Tile[" + symbol + ": " + description + itemInfo + "]";
    }
}