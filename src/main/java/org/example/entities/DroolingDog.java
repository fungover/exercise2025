package org.example.entities;

public class DroolingDog extends Enemy {

    public DroolingDog(int x, int y) {
        super("Drooling Dog", 10, 2, x, y);
    }


    @Override
    public void attack(Player player) {
        int damageDealt = getDamage();
        player.takeDamage(damageDealt);
        System.out.println("The " + getName() + " slobbers uncontrollably. You slip in the puddle and take " +
                damageDealt + " damage!");
    }
}
