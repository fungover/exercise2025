package ExerciseTwo.Service;

import ExerciseTwo.Entities.Enemy;
import ExerciseTwo.Entities.Inventory;

public class Commands {

    private final Enemy enemy;
    private final Inventory inventory = new Inventory();

    public Commands(Enemy enemy) {
        this.enemy = enemy;
    }

    public void handleCommand(String input) {

        if(!input.isEmpty()) {
            char command = input.charAt(0);

            switch (command) {
                case 'a': enemy.getAttack(); break;
                case 'i' : inventory.getInventory(); break;
                default:
                    System.out.println("Wrong command"); break;
            }

        }



    }

}
