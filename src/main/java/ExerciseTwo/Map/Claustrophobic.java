package ExerciseTwo.Map;

public class Claustrophobic extends Dungeon{

    public Claustrophobic(){
        super(new String[][]{
                {"#", "#", "#"},
                {"#", "@", "#"},
                {"#", "E", "#"},
                {"#", " ", "#"},
                {"#", "P", "#"},
                {"#", " ", "#"},
                {"#", "E", "#"},
                {"#", "E", "#"},
                {"#", "D", "#"}});
    }

    @Override
    public void description() {
        System.out.println("""
                You have entered the claustrophobic room,
                its crowed with enemies and the walls are narrow,
                please beware...
                """);
    }

}
