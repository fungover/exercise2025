package ExerciseTwo.Service;

public class Position {

    private int row;
    private int col;

    public Position(String[][] dungeonMap) {
        findPlayer(dungeonMap);
    }

    private void findPlayer(String[][] dungeonMap) {

        if(dungeonMap == null || dungeonMap.length == 1){
            throw new IllegalArgumentException("Map is null or empty");
        }

        for (int i = 0; i < dungeonMap.length; i++) {
            for (int j = 0; j < dungeonMap[0].length; j++) {
                if (dungeonMap[i][j].equals("@")) {
                    this.row = i;
                    this.col = j;
                    return;
                }
            }
        }
        throw new IllegalStateException("Player cant be found");
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row,  int col) {
        this.row = row;
        this.col = col;
    }
}
