package ExerciseTwo.Entities.Weapons;

import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Service.Emojis;

public class DiamondSword extends Item {

    public DiamondSword() {
        super("Diamond sword", -20);
    }

    @Override
    public void itemDescription() {
        System.out.println("You found a "+ type+" "+Emojis.sword +" white damage attack "+effect);
    }
}
