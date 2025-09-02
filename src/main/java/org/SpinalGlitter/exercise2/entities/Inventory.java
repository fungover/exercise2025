package org.SpinalGlitter.exercise2.entities;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<ItemObject> items;
    // TODO: create a object interface to store items in. Should be declared List<interface> items
    private int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }
    /** Add an item to the inventory if there is space.
     * @param item The item to add.
     * @return true if the item was added, false if the inventory is full.
     */
    public boolean addItem(ItemObject item) {
        if (isFull()) return false;
        items.add(item);
        return true;
    }

    /*public boolean removeItem(ItemObject item) {
        Potion potion = items.stream().filter(items -> items instanceof Potion).findFirst().orElse(null);
        if (potion != null) {
            return items.remove(item);
        }
    }*/
    public  boolean hasWeapon() {
        for (ItemObject it : items) {
            if (it instanceof Sword) return true;
        }
        return false;
    }

    public boolean consumeFirstPotion() {
    for (int i = 0; i < items.size(); i++) {
        ItemObject it = items.get(i);
        if (it instanceof Potion) {
            items.remove(i);
            return true;
        }
    }
    return false;
}


    public boolean isFull() {
        return items.size() >= capacity;
    }

    public boolean emptyInventory() {
        return items.isEmpty();
    }

    // show inventory items
    public void printItems() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Inventory (" + items.size() + "/" + capacity + "):");
            for (int i = 0; i < items.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + items.get(i).printItem());
            }
        }
    }

    public boolean hasPotion() {
        for (ItemObject it : items) {
            if (it instanceof Potion) return true;
        }
        return false;
    }

    // lagra allting i en arraylist
    // implemnetera ItemObject interface i potion och weapons

    // method addItem



    // method removeItem
    // method listItems
    // method isFull
    // method useItem
}

// TODO: Inventory
// 1. Create a class Inventory to manage items.
// List of items
   /* interface Foo {
        String bar();
    }

    class Impl1 implements Foo {
        String bar() {
            return "This is implementation 1";
        }
    }

    class Impl2 implements Foo {
        String bar() {
            return "This is implementation 2";
        }
    }

    public class Main {
        public static void main(String[] args) {
            Foo[] myArray = new Foo[2];
            myArray[0] = new Impl1();
            myArray[1] = new Impl2();
            for (int i = 0; i < myArray.length; i++) {
                System.out.println(myArray[i].bar());
            }
        }
    }*/
