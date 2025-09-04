package org.example.utils;
import org.example.entities.Item;

public class ItemOnMap {
    public Item item;
    public int x, y;

    public ItemOnMap(Item item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
    }
}
