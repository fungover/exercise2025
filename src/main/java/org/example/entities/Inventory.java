package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<Item>();
    public List<Item> getItems() { return items; }

    public void addItem(Item item) {
        items.add(item);
        System.out.println(item.getName() + " added to inventory!");
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
