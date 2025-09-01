package ExerciseTwo.Map;

import ExerciseTwo.Service.Emojis;

public class Dungeon {

    private String[][] dungeonMap;

    public Dungeon(){

        this.dungeonMap = new String[][]{
                {"#","#","#","#","#"},
                {"#","@"," "," ","#"},
                {"#"," ","E"," ","#"},
                {"#"," "," ","G","#"},
                {"#"," "," "," ","#"},
                {"#","#","#","D","#"}
        };
    }

    public Dungeon(String[][] dungeonMap) {
        this.dungeonMap = dungeonMap;
    }
    public void printMap(){

        for (int i = 0; i < dungeonMap.length; i++) {
            for (int j = 0; j < dungeonMap[i].length; j++) {
                String tile = dungeonMap[i][j];
                String emoji;

                switch (tile) {
                    case "#": emoji = Emojis.wall; break;
                    case "@": emoji = Emojis.player; break;
                    case "D": emoji = Emojis.door; break;
                    case "E": emoji = Emojis.enemy; break;
                    case "G": emoji = Emojis.gift; break;
                    default: emoji = Emojis.path; break;
                }

                System.out.print(" "+emoji);
            }
            System.out.println();
        }
    }

    public void placeEnemy(){}

}
