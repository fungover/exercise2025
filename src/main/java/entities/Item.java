package entities;

/**
 * @author Jörgen Lindström
 * @version 1.0
 */

import interfaces.Usable;
import interfaces.Displayable;

/**
 * Base class for all items in the game
 * Implement Usable och Displayable interfaces
 */
public abstract class Item implements Usable, Displayable {
    private String name;
    private String description;
    private boolean consumable;
    private char displaySymbol;

    // Constructor - runs when we create a new object
    public Item(String name, String description, boolean consumable) {
        this.name = name;
        this.description = description;
        this.consumable = consumable;
        this.displaySymbol = '?'; // Standard symbol
    }

    // Constructor - runs when we create a new object
    public Item(String name, String description, boolean consumable, char displaySymbol) {
        this.name = name;
        this.description = description;
        this.consumable = consumable;
        this.displaySymbol = displaySymbol;
    }

    // Getter methods - so other classes can read our private attributes
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isConsumable() {
        return consumable;
    }

    public char getDisplaySymbol() {
        return displaySymbol;
    }

    // Alias for getDisplaySymbol - used by theTile-class
    public char getSymbol() {
        return getDisplaySymbol();
    }

    public abstract String use(Player player);

    @Override
    public String toString() {
        return name + " - " + description;
    }
}