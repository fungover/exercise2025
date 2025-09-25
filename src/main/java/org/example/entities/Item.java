package org.example.entities;

/*
 * Represemterar ett föremål som kan hittas på en ruta och användas av Player
 * name = Namnet på föremålet
 * type = Typen av föremål (POTION, WEAPON, ARMOR)
 * effectValue = Värdet på föremålets effekt (ex potion helar +50 HP)
 */

public class Item {
    public enum ItemType {
        CONSUMABLE, WEAPON, ARMOR
    }

    private final String name;
    private final ItemType type;
    private final int value;

    public Item(String name, ItemType type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
