package ExerciseTwo.Entities.Enemy;

public class Zombie extends Enemy {

    public Zombie() {
        super("zombie", 20, -10);
    }

    @Override
    public void description() {
        System.out.println("You encounter a "+type+" with "+health+" hp");
    }
}
