package ExerciseTwo.Entities;

import ExerciseTwo.Service.PrintText;

public final class Player {
    
    private final String playerName;
    private int health;

    public Player(String playerName) {
        this.playerName = playerName;
        this.health = 100;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setHealth(int health) {
       this.health += health;

       if (this.health > 100) {
           this.health = 100;
       }

       if (this.health <= 0) {
           this.health = 0;
           System.out.println("You have been defeated!");
           System.exit(0);
       }
    }

    public int getHealth() {
        return health;
    }

    public void presentPlayer(){
        PrintText.printGreen("Let the adventure begin... "+playerName);
    }

}
