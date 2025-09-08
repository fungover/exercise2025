package ExerciseTwo.Entities.Inventory;

public class Potion extends Item{

    public Potion() {
        super("Potion", 25);
    }

    @Override
    public void itemDescription() {
        System.out.println("You found a "+type+" that restores "+effect+" hp if used");
    }
}
