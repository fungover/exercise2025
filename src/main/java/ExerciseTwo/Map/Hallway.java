package ExerciseTwo.Map;

public class Hallway extends Dungeon{

    public Hallway(){
        super(new String[][]{
                {"#", "#", "#", "#", "#"},
                {"#", "@", " ", " ", "#"},
                {"#", " ", "G", " ", "#"},
                {"#", " ", " ", "E", "#"},
                {"#", "#", "#", "D", "#"}});
    }

    public void content(){
        System.out.println("You found a healing potion");
        //inventory.add(content)
    }

    @Override
    public void playerPosition() {
        System.out.println("Hallway player position");

    }

    @Override
    public void description() {
        System.out.println("You have entered the hallway");
    }

}
