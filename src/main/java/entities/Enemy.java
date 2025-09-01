package entities;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class Enemy {
    private final UUID id;
    private String type;
    private int health;
    private int attack;
    private String loot;

    private static final List<Enemy> ENEMIES = new ArrayList<>();

    public Enemy(String type, int health, int attack, String loot){
        this.id = UUID.randomUUID();
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.loot = loot;
    }

    public static Enemy create(String type, int health, int attack, String loot) {
        Enemy e = new Enemy(type, health, attack, loot);
        ENEMIES.add(e);
        return e;
    }

    // === Predefined Enemies ===
    public static final Enemy Spider = create("Spider", 10, 1, "Gold Coin");
    public static final Enemy Skeleton = create("Skeleton", 20, 5, "Bone");
    public static final Enemy DRAGON = create("Dragon", 200, 25, "Dragon Scale");

    //Access to enemy list
    public static List<Enemy> getAllEnemies() {
        return Collections.unmodifiableList(ENEMIES);
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public String getLoot() {
        return loot;
    }

}
