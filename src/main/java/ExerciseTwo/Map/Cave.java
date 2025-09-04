package ExerciseTwo.Map;

public class Cave extends Dungeon{

    public Cave(){
        super(new String[][]{
                {"#", "#", "#", "#", "#"},
                {"#", " ", " ", "@", "#"},
                {"#", " ", "G", " ", "#"},
                {"#", " ", " ", "E", "#"},
                {"#", "#", "#", "D", "#"}});
    }

    @Override
    public void description() {
        System.out.println("You have entered the cave");
    }
}
