package org.example.entities.items;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public Inventory(List<Item> items) {
        this.items = new ArrayList<>(items);
    }

    //Getters
    public List<Item> getItems() {
        return items;
    }

    //Setters
    public void setItems(List<Item> items) {
        this.items = items;
    }

    //Methods
    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(Item item){
        items.remove(item);
    }
}
