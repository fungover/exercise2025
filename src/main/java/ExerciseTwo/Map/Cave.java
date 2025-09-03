package ExerciseTwo.Map;

public class Cave extends Dungeon{

    public Cave(){
        super(new String[][]{
                {"#", "#", "#", "#", "#"},
                {"#", "@", " ", " ", "#"},
                {"#", " ", "G", " ", "#"},
                {"#", " ", " ", "E", "#"},
                {"#", "#", "#", "D", "#"}}, "Cave");
    }

    @Override
    public void content() {
        System.out.println("You found a healing potion");
    }

    @Override
    public void description() {
        System.out.println("You have entered the cave");
    }
}
