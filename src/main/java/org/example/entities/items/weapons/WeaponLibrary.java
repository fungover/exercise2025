package org.example.entities.items.weapons;

import org.example.entities.items.Weapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponLibrary {
    public static final List<Weapon> weapons = new ArrayList<>();

    static {
        weapons.add(new Weapon("Sword of Slight Inconvenience", "Cuts things… sometimes.", 15, 25));
        weapons.add(new Weapon("Axe of Mild Discomfort", "Not too sharp, but can still hurt.", 20, 30));
        weapons.add(new Weapon("Dagger of Questionable Safety", "Small but sneaky.", 5, 15));
        weapons.add(new Weapon("Hammer of Somewhat Unpredictable Outcomes", "Heavy and clunky.", 25, 35));
        weapons.add(new Weapon("Mace of Minor Annoyance", "Blunt and painful.", 18, 28));
        weapons.add(new Weapon("Spear of Occasional Accuracy", "Long reach, decent damage.", 12, 22));
    }
}
