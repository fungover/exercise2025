package Enemy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class Enemy {
    private final UUID id;
    protected String type;
    protected int health;
    protected int attack;
    protected int x;
    protected int y;

    private static final List<Enemy> ENEMIES = new ArrayList<>();

    public Enemy(String type, int health, int attack,int x,int y){
        this.id = UUID.randomUUID();
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.x = x;
        this.y = y;
        ENEMIES.add(this);
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
    public int getX() { return x; }
    public int getY() { return y; }


}
