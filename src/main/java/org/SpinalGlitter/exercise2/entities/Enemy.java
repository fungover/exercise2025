package org.SpinalGlitter.exercise2.entities;
import org.SpinalGlitter.exercise2.entities.Player;


public class Enemy {
    private String name;
    private int hp;
    private int damage;
    private Position position;

    public Enemy(String name, int hp, int damage, Position position) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int damage) {
        this.hp -= damage;
    }

    // enemies attack damage
   public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return hp > 0;

    }

    public Position getPosition() {
        return position;
    }
}

/*public void enemyAttack(Player player, Enemy enemy) {
    System.out.println(enemy.getName() + " attacks you for " + enemy.getDamage() + " damage!");
    player.setHp(player.getHp() - enemy.getDamage());
    System.out.println("You now have " + player.getHp() + " HP left.");
}*/


// Type
