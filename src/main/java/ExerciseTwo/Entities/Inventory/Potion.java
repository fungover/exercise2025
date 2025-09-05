package ExerciseTwo.Entities.Inventory;

public class Potion extends Item{

    public Potion() {
        super("Potion", 25);
    }

    @Override
    public void itemDescription() {
        System.out.println(type+" found. restores "+effect+" hp if used");
    }
}
