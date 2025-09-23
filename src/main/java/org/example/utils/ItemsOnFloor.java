package org.example.utils;

import org.example.entities.items.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemsOnFloor {
    private final List<Item> items = new ArrayList<>();

    public void addToList(Item item) {
        items.add(item);
    }

    public Item getItemOnFloor(int x, int y) {
        for (Item item : items) {
            if (item.getX() == x && item.getY() == y) {
                return item;
            }
        }
        System.out.println("There is no item here.");
        return null;
    }

}
