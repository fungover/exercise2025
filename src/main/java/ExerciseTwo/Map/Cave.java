package ExerciseTwo.Map;

public class Cave extends Dungeon{

    private int playerCol;
    private int playerRow;

    public Cave(int playerCol, int playerRow) {
        super(playerCol, playerRow);
    }

    public Cave(){
        super(new String[][]{
                {"#", "#", "#", "#", "#"},
                {"#", "@", " ", " ", "#"},
                {"#", " ", "G", " ", "#"},
                {"#", " ", " ", "E", "#"},
                {"#", "#", "#", "D", "#"}}, "Cave");
    }

    public void setPlayerCol(int playerCol) {
        this.playerCol = playerCol;
    }

    public void setPlayerRow(int playerRow) {
        this.playerRow = playerRow;
    }

    public void content(int col, int row){
        if(col == 2 && row == 2){
            System.out.println("You found a healing potion");
        }

        //inventory.add(content)
    }

    @Override
    public void description() {
        System.out.println("You have entered the cave");
    }
}
