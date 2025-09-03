package ExerciseTwo.Map;

public class Hallway extends Dungeon{

    public Hallway(){
        super(new String[][]{
                {"#", "#", "#", "#", "#"},
                {"#", "@", " ", " ", "#"},
                {"#", " ", "G", " ", "#"},
                {"#", " ", " ", "E", "#"},
                {"#", "#", "#", "D", "#"}}, "Hallway");
    }

    public void content(){
            System.out.println("You found a healing potion");
        //inventory.add(content)
    }

    @Override
    public void description() {
        System.out.println("You have entered a dark hallway");
        System.out.println("Defeat the enemy to proceed your journey");
    }

}
