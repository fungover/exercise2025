package ExerciseTwo.Entities;

import ExerciseTwo.Entities.Weapons.ThunderSword;
import ExerciseTwo.Entities.Weapons.Weapon;
import ExerciseTwo.Utils.PrintText;

public final class Player {
    
    private String playerName;
    private int health;
    private int weapon;

    public Player(String playerName) {
        this.playerName = playerName;
        this.health = 100;
        this.weapon = new ThunderSword().getDamage();
    }
    public Player(){}

    public void setHealth(int health) {
        this.health += health;


       if (this.health > 100) {
           this.health = 100;
       }

       if (this.health <= 0) {
           this.health = 0;
       }
    }

    public int getHealth() {
        return health;
    }

    public void setWeapon(Weapon weapon){
        this.weapon = weapon.getDamage();
    }

    public int getWeapon(){
        return weapon;
    }

    public void presentPlayer(){
        PrintText.printGreen("Let the adventure begin... "+playerName);
    }

    public void playerHealth(){
        System.out.println("You now have "+health+" health");
    }

}
