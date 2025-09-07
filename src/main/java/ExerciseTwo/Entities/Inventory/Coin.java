package ExerciseTwo.Entities.Inventory;

public class Coin extends Item{
    public Coin() {
        super("Coin", 20);
    }

    @Override
    public void itemDescription() {
        System.out.println("You found a "+type+", with value of "+effect);
    }
}
