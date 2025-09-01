package org.example.entities.items.consumables;

import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Position;
import org.example.entities.equipment.Usable;

public class HealthPotion extends Item implements Usable {

    private final int healAmount;

    public HealthPotion(Position position) {
        super("Health Potion", "Restores 30 health points.", position);
        this.healAmount = 30;
    }

    @Override
    public void use(Player player) {
        System.out.println("You drink the " + name + "!");
        player.heal(healAmount);
        System.out.println("You feel better! Restored " + healAmount + "health.");
    }
}
