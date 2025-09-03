package ExerciseTwo.Entities;

import ExerciseTwo.Service.PrintText;

public final class Player {
    
    private String playerName;
    private int attack;
    private int health;

    public Player(String playerName) {
        this.playerName = playerName;
        this.attack = -10;
        this.health = 100;
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

    public int getPlayerAttack(){
        return attack;
    }

    public void presentPlayer(){
        PrintText.printGreen("Let the adventure begin... "+playerName);
    }

}
