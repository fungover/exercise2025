package ExerciseTwo.Entities.Weapons;

import ExerciseTwo.Entities.Inventory.Item;

public class Sword extends Item {

    public Sword() {
        super("Basic sword", -10);
    }

    @Override
    public void itemDescription() {
        System.out.println("You found "+type+" white damage attack "+effect);
    }
}
