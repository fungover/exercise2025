package org.example.entities;

public class Mob {

    private int health;

    public Mob(String name, Health health, int strength, int x, int y) {
        this.health = health.health();
    }

    public Mob(String name, Pos pos) {
        this(name, new Health(10), 10, pos.x(), pos.y());
    }


    static void main() {
        //var mob = new MobBuilder().setName("Spider").setPos(new Pos(1, 1)).createMob();
        var mob = new MobBuilder()
                .setName("Dragon")
                .setStrength(10)
                .createMob();
    }
}
