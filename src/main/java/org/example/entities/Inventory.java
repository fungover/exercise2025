package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<Item>();

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public void addItem(Item item) {
        items.add(item);
        System.out.println(item.getName() + " added to inventory!");
    }

    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public void useItem(int index, Character character) {
        if(index >= 0 && index < items.size()) {
            items.get(index).use(character);
            items.remove(index);
        }
    }

    public void showItems() {
        System.out.println("Inventory:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println(i +": " + items.get(i).getName());
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
