package ExerciseTwo.Entities;

public class Coin extends Item{
    public Coin() {
        super("Coin", 20);
    }

    @Override
    public void itemFound() {
        System.out.println("You found a"+type+", with value "+effect);
    }
}
