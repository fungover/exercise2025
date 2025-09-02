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

    public boolean addItem(ItemObject item) {
        if (isFull()) return false;
        items.add(item);
        return true;
    }

    public boolean removeItem(ItemObject item) {
        return items.remove(item);
    }

    public boolean isFull() {
        return items.size() >= capacity;
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
