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
        System.out.println("""
    You have entered the hallway to the dungeons.
    Your journey know begins... Good Luck!
    """);
    }

}
