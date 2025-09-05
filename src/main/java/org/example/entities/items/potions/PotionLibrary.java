package org.example.entities.items.potions;

import org.example.entities.items.Potion;

import java.util.ArrayList;
import java.util.List;

public class PotionLibrary {
    public static final List<Potion> potions = new ArrayList<>();

    static {
        potions.add(new HealthPotion("Health Potion", "Restores health.", 20, 40));
    }
}
