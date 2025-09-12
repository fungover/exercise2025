package entities;

import items.MagicKey;
import interfaces.Combatable;
import interfaces.Displayable;
import interfaces.Positionable;
import utils.Constants;
import java.util.ArrayList;
import java.util.List;

/**
 * Spelarklassen utökad med inventarium och föremålshantering
 */
public class Player extends Entity {
    // Inventarium - en lista av föremål
    private List<Item> inventory;
    private int maxInventorySize;
    private int damageBonus; // Extra skada från vapen

    // Konstruktor
    public Player(String name, int maxHealth, int damage) {
        super(name, maxHealth, damage);
        this.inventory = new ArrayList<>();
        this.maxInventorySize = 10; // Spelaren kan bära max 10 föremål
        this.damageBonus = 0; // Börjar utan extra skada
    }

    // Implementerar abstract metod från Entity - DENNA ÄR VIKTIG!
    @Override
    public String getDescription() {
        return "En modig piratkapten vid namn " + getName() +
                " med " + getCurrentHealth() + "/" + getMaxHealth() + " hälsa och " +
                getTotalDamage() + " i total skada.";
    }

    // Lägg till föremål i inventarium
    public boolean addItem(Item item) {
        if (inventory.size() >= maxInventorySize) {
            return false; // Inventariet är fullt
        }
        inventory.add(item);
        return true;
    }

    // Ta bort föremål från inventarium
    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    // Hitta föremål i inventarium baserat på namn
    public Item findItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
                return item;
            }
        }
        return null; // Föremålet hittades inte
    }

    // Använd ett föremål
    public String useItem(String itemName) {
        Item item = findItem(itemName);

        if (item == null) {
            return "Du har inget föremål som heter '" + itemName + "' i ditt inventarium.";
        }

        // Använd föremålet
        String result = item.use(this);

        // Om föremålet är consumable (förbrukas), ta bort det från inventarium
        if (item.isConsumable()) {
            removeItem(item);
        }

        return result;
    }

    // Visa inventarium
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

    // Läk spelaren (används av rom-flaskor) - override från Entity för specifik logik
    @Override
    public void heal(int amount) {
        int newHealth = Math.min(getCurrentHealth() + amount, getMaxHealth());
        setCurrentHealth(newHealth);
    }

    // Lägg till skadebonus från vapen
    public void addDamageBonus(int bonus) {
        this.damageBonus += bonus;
    }

    // Ta bort skadebonus från vapen
    public void removeDamageBonus(int bonus) {
        this.damageBonus -= bonus;
        if (this.damageBonus < 0) {
            this.damageBonus = 0;
        }
    }

    // Få total skada (bas + bonus)
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

    // Kontrollera om spelaren har en magisk nyckel
    public boolean hasMagicKey() {
        for (Item item : inventory) {
            if (item instanceof MagicKey) {
                return true;
            }
        }
        return false;
    }

    // Metoder som PirateCave behöver
    public int[] getDisplayPosition() {
        // Konverterar spelposition till display-koordinater
        int displayX = getX() * 2 + 1;
        int displayY = getY() * 2 + 1;
        return new int[]{displayX, displayY};
    }

    // Implementerar Combatable interface
    public int attack() {
        return getTotalDamage();
    }

    // Implementerar Displayable interface
    public char getSymbol() {
        return Constants.PLAYER_SYMBOL;
    }

    // Implementerar Positionable interface (ärvs redan från Entity, men för tydlighet)
}