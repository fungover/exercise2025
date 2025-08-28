package org.example.entities;

public class DroolingDog extends Enemy{
    public DroolingDog(int x, int y){
        super("Drooling Dog", 10, 2, x, y);
    }

    @Override
    public void attack(Player player) {
        player.takeDamage(damage);
        System.out.println("The Drooling Dog slobbers uncontrollably. You slip in the puddle and take " + damage + " damage!");
    }
}
