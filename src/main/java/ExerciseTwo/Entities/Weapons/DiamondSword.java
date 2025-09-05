package ExerciseTwo.Entities.Weapons;

import ExerciseTwo.Entities.Inventory.Item;

public class DiamondSword extends Item {

    public DiamondSword() {
        super("Diamond sword", -20);
    }

    @Override
    public void itemDescription() {
        System.out.println("You found "+type+" white damage attack "+effect);
    }
}
