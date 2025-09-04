package ExerciseTwo.Map;

public class Hallway extends Dungeon{

    public Hallway(){
        super(new String[][]{
                {"#", "#", "#", "#", "#"},
                {"#", "@", "E", " ", "#"},
                {"#", "P", "G", " ", "#"},
                {"#", " ", " ", "E", "#"},
                {"#", "#", "#", "D", "#"}});
    }

    @Override
    public void description() {
        System.out.println("You have entered a dark hallway");
    }

}
