package ExerciseTwo.Game;

import ExerciseTwo.Entities.Inventory;

public class PlayerInput {
    public boolean commandInput(String input) {

        switch (input) {
            case "i":
                Inventory inventory = new Inventory();
                inventory.getInventory();
                return true;
            case "h":
                InputHandling.commands();
                return true;
                case "q": System.exit(0);
                default: return false;
        }
    }
}
