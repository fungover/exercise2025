package ExerciseTwo.Entities;

import java.util.HashMap;

//HashMap för att göra det möjligt att koppla höger hand till ett vapen, bara kunna hålla ett vapen åt gången
public class Weapon {

    private final String description;
    private final int damage;
    private final HashMap<String, Integer> weapons = new HashMap<>();

    public Weapon() {
        this.description = "Thunder sword";
        this.damage = 20;
        weapons.put("Sword", 10);
    }

    public Weapon(String description, int damage) {
        this.description = description;
        this.damage = damage;
        weapons.put(description, damage);
    }

    public HashMap<String, Integer> getWeapons() {
        return weapons;
    }

    public void attack(){
        System.out.println("You hit the enemy with your "+description+" and make "+damage+" in damage");
    }

}
