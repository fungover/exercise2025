package org.example.entities.items;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.entities.characters.Player;
import org.example.entities.items.equippables.Armor;
import org.example.entities.items.equippables.Weapon;

/**
 * Player's bag + equipped gear + short-lived combat effects.
 * Knows nothing about concrete item classes; works with capabilities.
 */
public final class Inventory {
    private final List<Item> backpack = new ArrayList<>();
    private Weapon equippedWeapon;
    private Armor  equippedArmor;

    // One-time bonus from DamagePotion, consumed by combat when the player attacks.
    private int nextAttackBonus = 0;


    public void add(Item item) {
        if (item != null) backpack.add(item);
    }
    public void remove(Item item) {
        backpack.remove(item);
    }
    public List<Item> items() {
        return List.copyOf(backpack);
    }

    /**
     * Use an item:
     * - Consumable: apply effect and remove from backpack.
     * - Equippable: equip into the right slot (replaces current).
     */

    public boolean use(Item item, Player player) {
        return (item != null) && item.onUse(player, this);
    }

    /** Equip an item into its slot (WEAPON/ARMOR). */
    public void equip(Equippable equippable, Player player) {
        switch (equippable.slot()) {
            case WEAPON -> {
                if (equippedWeapon != null) equippedWeapon.onUnequip(player);
                equippedWeapon = (Weapon) equippable;
                equippedWeapon.onEquip(player);
            }
            case ARMOR -> {
                if (equippedArmor != null) equippedArmor.onUnequip(player);
                equippedArmor = (Armor) equippable;
                equippedArmor.onEquip(player);
            }
        }
    }

    public Weapon weapon() {
        return equippedWeapon;
    }

    public Armor  armor()  {
        return equippedArmor;
    }

    /** Add a one-time bonus to the next attack (stackable). */
    public void grantNextAttackBonus(int amount) {
        if (amount > 0) {
            nextAttackBonus += amount;
        }
    }
    public int takeNextAttackBonus() {
        int b = nextAttackBonus; nextAttackBonus = 0; return b;
    }

    public String summary() {
        return backpack.isEmpty()
                ? "[]"
                : backpack.stream().map(Item::name).collect(Collectors.joining(", ", "[", "]"));
    }
    public String equippedWeaponName() {
        return equippedWeapon != null ? equippedWeapon.name() : "None";
    }

    public String equippedArmorName()  {
        return equippedArmor  != null ? equippedArmor.name()  : "None";
    }
}
