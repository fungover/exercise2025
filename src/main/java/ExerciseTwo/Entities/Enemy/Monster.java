package ExerciseTwo.Entities.Enemy;

public class Monster extends Enemy {

    public Monster() {
        super("monster", 15, -5);
    }

    @Override
    public void description() {
        System.out.println("The encounter a "+ type +" with "+health+" hp");
    }

}

