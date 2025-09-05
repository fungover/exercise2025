package org.example.entities;

interface AttackBehavior {
    void attack(Player player);
    int getDamage();
    String toString();
}

public abstract class Enemy {
    private final String name;
    private int health;
    private final AttackBehavior attackBehavior;

    protected Enemy(String name, int health, AttackBehavior attackBehavior) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Enemy name cannot be null or empty");
        }
        if (health <= 0) {
            throw new IllegalArgumentException("Health cannot be negative or zero");
        }
        if (attackBehavior == null) {
            throw new IllegalArgumentException("Attack behavior cannot be null");
        }
        this.name = name;
        this.health = health;
        this.attackBehavior = attackBehavior;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public AttackBehavior getAttackBehavior() {
        return attackBehavior;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        health = Math.max(0, health - damage);
    }

    public void attack(Player player) {
        attackBehavior.attack(player);
    }

    @Override
    public String toString() {
        return name + " (" + health + ") " + attackBehavior;
    }
}

// --- ENEMIES --------------------------------------------------------------------
// --- Add only enemies inside this scope -----------------------------------------
// ----START OF SCOPE--------------------------------------------------------------

// Goblin
class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 30, new BasicAttack(5));
    }
}

// Troll
class Troll extends Enemy {
    public Troll() {
        super("Troll", 50, new BasicAttack(10));
    }
}

// ----END OF SCOPE----------------------------------------------------------------

class BasicAttack implements AttackBehavior {
    private final int damage;
    
    public BasicAttack(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        this.damage = damage;
    }

    @Override
    public void attack(Player player) {
        // For later implementation
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "+" + damage + " damage";
    }
}