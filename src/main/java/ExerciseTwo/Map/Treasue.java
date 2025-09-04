package ExerciseTwo.Map;

public class Treasue extends Dungeon{

    public Treasue(){
        super(new String[][]{
                {"#", "#", "#", "#"},
                {"#", "@", " ", "#"},
                {"#", " ", " ", "#"},
                {"#", " ", "E", "#"},
                {"#", "#", "T", "#"}});
    }

    @Override
    public void description() {
        System.out.println("You have entered the treasure room, the goal is near");
    }

}
