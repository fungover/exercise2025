package entities;

public abstract class EnemyBase implements Enemy {
    protected final String type;
    protected final int maxHealth;
    protected int health;
    protected int damage;
    protected int x;
    protected int y;

    public EnemyBase(String type, int health, int damage, int startX, int startY) {
        this.type = type;
        this.maxHealth = Math.max(1, health);
        this.health = this.maxHealth;
        this.damage = Math.max(1, damage);
        this.x = startX;
        this.y = startY;
    }

    // --- Enemy/Creature ---
    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void takeDamage(int amount) {
        if (amount <= 0) return;
        health = Math.max(0, health - amount);
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    // --- Enemy-specifika ---
    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** Fienden attackerar spelaren. */
    public void attack(Player player) {
        if (player != null && player.isAlive()) {
            player.takeDamage(damage);
        }
    }

    /** Placeholder för enkel AI */
    public void takeTurn(Player player) {
        if (player != null && player.getX() == x && player.getY() == y) {
            attack(player);
        }
    }
}
