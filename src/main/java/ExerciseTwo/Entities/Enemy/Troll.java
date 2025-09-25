package ExerciseTwo.Entities.Enemy;

public class Troll extends Enemy {

    public Troll() {
        super("troll", 20, -20);
    }

    @Override
    public void description() {
        System.out.println("You encounter a hugh "+ type +" with "+health+" hp");
    }

}
