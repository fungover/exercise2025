package ExerciseTwo.Entities;

import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Entities.Weapons.Sword;
import ExerciseTwo.Utils.PrintText;

public final class Player {
    
    private String playerName;
    private int health;
    private int weapon;

    public Player(String playerName) {
        this.playerName = playerName;
        this.health = 100;
        this.weapon = new Sword().getEffect();
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

    public void setWeapon(Item item){
        this.weapon = item.getEffect();
        PrintText.printYellow("You are know equipped with "+item.getType()+" which causes "+item.getEffect()+" in damage");
    }

    public int getWeapon(){
        return weapon;
    }

    public void presentPlayer(){
        PrintText.printGreen("Let the adventure begin... "+playerName);
    }

    public void playerHealth(){
        PrintText.printYellow("You now have "+health+" health");
    }

}
