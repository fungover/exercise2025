package org.game.entities;

import org.game.utils.Colors;

import java.util.HashMap;
import java.util.Map;


public class Player extends Character {
private Map<String,Item> inventory = new HashMap<>();
private Map<String,Item> equipment = new HashMap<>();

    public Player(String name, int x, int y) {
    super(
            name,
            20,
            10,
            5,
            5,
            5,
            5,
            5,
            x,
            y);

    }

    public void addItem(Item item) {
        if (inventory.containsKey(item.getName())) {
            Item existingItem = inventory.get(item.getName());
            existingItem.addQuantity(item.getQuantity());
            System.out.println("Picked up " + item.getQuantity() + " x " + item.getName());
        } else {
            inventory.put(item.getName(), item);
            System.out.println("Picked up " + item.getQuantity() + " x " + item.getName());
        }
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("No items in your inventory");

        } else {
            System.out.println(Colors.cyanColor+ "==============Inventory====================");
            System.out.println("You have " + inventory.size() + " items in your inventory");
           for (Item item : inventory.values()) {
               System.out.println(Colors.cyanColor + " * " + item.getName() + " x " + item.getQuantity() + Colors.resetColor);
           }

        }
    }

    public void equipItem(String itemName) {
        Item itemToEquip = null;

        for (Item item : inventory.values()) {
            if (item.getName().equals(itemName)) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    itemToEquip = item;
                    break;
                }
            }
        }

        if (itemToEquip == null) {
            System.out.println("Item " + itemName + " not found in your inventory");
            return;
        }

        if (!itemToEquip.isEquippable()) {
            System.out.println("Item " + itemName + " is not able to be equipped");
            return;
        }

        for (String slot : itemToEquip.getSlots()) {
            if (equipment.containsKey(slot)) {
                Item old = equipment.remove(slot);
                addItem(old);
                System.out.println("Unequipped " + old.getName() + " from " + slot);
            }

            equipment.put(slot,new Item(
                    itemToEquip.getName(),
                    itemToEquip.getType(),
                    itemToEquip.getPrice(),
                    1,
                    itemToEquip.getSlots()
            ));
        }


        itemToEquip.removeQuantity(1);
        if (itemToEquip.getQuantity() <= 0) {
            inventory.remove(itemToEquip.getName());
        }

        System.out.println("Equipped " + itemToEquip.getName());

    }


    public void showEquipment() {
        if (equipment.isEmpty()) {
            System.out.println("No items in your equipment");
        } else  {
            System.out.println(Colors.cyanColor + "===============Equipment==================");
            for (Map.Entry<String, Item> entry : equipment.entrySet()) {
                System.out.println(Colors.cyanColor + " * " + entry.getKey() + " [" + entry.getValue().getName() + "]" +Colors.resetColor);
            }
        }
    }

    public void unEquipItem(String slot) {
        if (equipment.containsKey(slot)) {
            Item removed = equipment.remove(slot);
            System.out.println("Unequipped " + removed.getName() + " from slot " + slot);
        } else  {
            System.out.println("Nothing equipped in your " + slot + " slot");
        }
    }
}