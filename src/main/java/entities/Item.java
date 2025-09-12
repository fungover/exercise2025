package entities;

import interfaces.Usable;
import interfaces.Displayable;

/**
 * Basklass för alla föremål i spelet
 * Implementerar Usable och Displayable interfaces
 */
public abstract class Item implements Usable, Displayable {
    // Privata attribut - bara denna klass kan komma åt dem direkt
    private String name;        // Föremålets namn
    private String description; // Beskrivning av föremålet
    private boolean consumable; // Kan föremålet förbrukas? (som rom-flaskan)
    private char displaySymbol; // Symbol som visas på kartan

    // Konstruktor - körs när vi skapar ett nytt föremål
    public Item(String name, String description, boolean consumable) {
        this.name = name;
        this.description = description;
        this.consumable = consumable;
        this.displaySymbol = '?'; // Standard symbol
    }

    // Konstruktor med displaySymbol
    public Item(String name, String description, boolean consumable, char displaySymbol) {
        this.name = name;
        this.description = description;
        this.consumable = consumable;
        this.displaySymbol = displaySymbol;
    }

    // Getter-metoder - så andra klasser kan läsa våra privata attribut
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

    // Alias för getDisplaySymbol - används av Tile-klassen
    public char getSymbol() {
        return getDisplaySymbol();
    }

    // Metod som PirateCave kanske letar efter
    public char getDisplayPosition() {
        return getDisplaySymbol();
    }

    // Abstract metod - alla subklasser MÅSTE implementera denna
    // Varje föremålstyp bestämmer själv vad som händer när det används
    public abstract String use(Player player);

    // Vanlig metod som alla subklasser ärver
    @Override
    public String toString() {
        return name + " - " + description;
    }
}