package ExerciseTwo.Game;

import ExerciseTwo.Entities.Inventory.Inventory;
import ExerciseTwo.Entities.Player;

public class PlayerInput {

    Inventory inventory;
    Player player;

    public PlayerInput(Inventory inventory, Player player) {
        this.inventory = inventory;
        this.player = player;
    }

    public boolean commandInput(String inputFromPlayer) {

        switch (inputFromPlayer) {
            case "i" : inventory.getInventory(); return true;
            case "h":  InputHandling.commands(); return true;
            case "p" : player.playerHealth(); return true;
            case "quit": System.exit(0);
            default: return false;
        }
    }
}
