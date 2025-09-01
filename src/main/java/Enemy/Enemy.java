package Enemy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class Enemy {
    private final UUID id;
    protected String type;   // protected så subklasser kan komma åt
    protected int health;
    protected int attack;
    protected String loot;

    private static final List<Enemy> ENEMIES = new ArrayList<>();

    public Enemy(String type, int health, int attack, String loot){
        this.id = UUID.randomUUID();
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.loot = loot;
        ENEMIES.add(this); // läggs alltid till i listan
    }

    // Access to enemy list
    public static List<Enemy> getAllEnemies() {
        return Collections.unmodifiableList(ENEMIES);
    }

    // Getters
    public UUID getId() { return id; }
    public String getType() { return type; }
    public int getHealth() { return health; }
    public int getAttack() { return attack; }
    public String getLoot() { return loot; }


}
