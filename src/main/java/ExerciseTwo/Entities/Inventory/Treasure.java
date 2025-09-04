package ExerciseTwo.Entities.Inventory;

public class Treasure extends Item  {

    public Treasure() {
        super("Diamond", 1000);
    }

    @Override
    public void itemFound() {
        System.out.println("You found the secret treasure a "+type+" with a value of "+effect);
    }
}
