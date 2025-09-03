package org.example.entities;

interface Effect {
    void apply(Player player);
    int getValue();
    String toString();
}

public abstract class Item {
    private final String name;
    private final Effect effect;

    protected Item(String name, Effect effect) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (effect == null) {
            throw new IllegalArgumentException("Effect cannot be null");
        }
        this.name = name;
        this.effect = effect;
    }
    public String getName() {
        return name;
    }

    public abstract ItemType getType();

    public Effect getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return name + " (" + getType() + ", " + effect + ")";
    }
}

class Weapon extends Item {
    public Weapon(String name, int damage) {
        super(name, new DamageEffect(damage));
    }

    @Override
    public ItemType getType() {
        return ItemType.WEAPON;
    }
}

class Potion extends Item {
    public Potion(String name, int healthRestored) {
        super(name, new HealingEffect(healthRestored));
    }

    @Override
    public ItemType getType() {
        return ItemType.POTION;
    }
}

class DamageEffect implements Effect {
    private final int damage;

    public DamageEffect(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        this.damage = damage;
    }

    @Override
    public void apply(Player player) {
        // for later implementation
    }

    @Override
    public int getValue() {
        return damage;
    }

    @Override
    public String toString() {
        return "Damage: " + damage;
    }
}

class HealingEffect implements Effect {
    private final int healthRestored;

    public HealingEffect(int healthRestored) {
        if (healthRestored < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.healthRestored = healthRestored;
    }

    @Override
    public void apply(Player player) {
        // for later implementation
    }

    public int getValue() {
        return healthRestored;
    }

    public String toString() {
        return "Health: " + healthRestored;
    }
}