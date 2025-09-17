package entities;

/**
 * @author Jörgen Lindström
 * @version 1.0
 */

import items.MagicKey;
import utils.Constants;
import java.util.ArrayList;
import java.util.List;


public class Player extends Entity {
    // Inventarium - en lista av föremål
    private List<Item> inventory;
    private int maxInventorySize;
    private int damageBonus; // Extra skada från vapen

    // Constructor
    public Player(String name, int maxHealth, int damage) {
        super(name, maxHealth, damage);
        this.inventory = new ArrayList<>();
        this.maxInventorySize = 10; // Spelaren kan bära max 10 föremål
        this.damageBonus = 0; // Börjar utan extra skada
    }

    // Implementing abstract metod from Entity
    @Override
    public String getDescription() {
        return "Du är nu en modig piratkapten vid namn " + getName() +
                " med " + getCurrentHealth() + "/" + getMaxHealth() + " hälsa och " +
                getTotalDamage() + " i total skada.";
    }

    // Add items to inventory
    public boolean addItem(Item item) {
        if (inventory.size() >= maxInventorySize) {
            return false; // Inventariet är fullt
        }
        inventory.add(item);
        return true;
    }

    // Remove items from inventory
    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    // Find items in inventory based on name
    public Item findItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                return item;
            }
        }
        return null;
    }

    // Use an item
    public String useItem(String itemName) {
        Item item = findItem(itemName);

        if (item == null) {
            return "Du har inget föremål som heter '" + itemName + "' i ditt inventarium.";
        }

        // Use an item
        String result = item.use(this);

        // If the item is consumable, remove it from inventory.
        if (item.isConsumable()) {
            removeItem(item);
        }

        return result;
    }

    // show inventory
    public String showInventory() {
        if (inventory.isEmpty()) {
            return "Ditt inventarium är tomt.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== INVENTARIUM ===\n");
        for (int i = 0; i < inventory.size(); i++) {
            sb.append((i + 1)).append(". ").append(inventory.get(i).toString()).append("\n");
        }
        sb.append("Platser använda: ").append(inventory.size()).append("/").append(maxInventorySize);

        return sb.toString();
    }

    // Heal the player (used by rum bottles) - override from Entity for specific logic
    @Override
    public void heal(int amount) {
        int newHealth = Math.min(getCurrentHealth() + amount, getMaxHealth());
        setCurrentHealth(newHealth);
    }

    // Add damage bonus from weapons
    public void addDamageBonus(int bonus) {
        this.damageBonus += bonus;
    }

    // Remove damage bonus from weapons
    public void removeDamageBonus(int bonus) {
        this.damageBonus -= bonus;
        if (this.damageBonus < 0) {
            this.damageBonus = 0;
        }
    }

    // Receive total damage (base + bonus)
    public int getTotalDamage() {
        return getDamage() + damageBonus;
    }

    // Getters
    public List<Item> getInventory() {
        return new ArrayList<>(inventory); // Returnera en kopia för säkerhet
    }

    public int getMaxInventorySize() {
        return maxInventorySize;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    // Check if the player has a magic key
    public boolean hasMagicKey() {
        for (Item item : inventory) {
            if (item instanceof MagicKey) {
                return true;
            }
        }
        return false;
    }

    // Methods that PirateCave needs
    public int[] getDisplayPosition() {
        // Konverterar spelposition till display-koordinater
        int displayX = getX() * 2 + 1;
        int displayY = getY() * 2 + 1;
        return new int[]{displayX, displayY};
    }

    // Implements Combatable interface
    public int attack() {
        return getTotalDamage();
    }

    // Implements Displayable interface
    public char getSymbol() {
        return Constants.PLAYER_SYMBOL;
    }

}