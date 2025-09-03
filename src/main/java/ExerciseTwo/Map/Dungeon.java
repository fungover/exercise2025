package ExerciseTwo.Map;

import ExerciseTwo.Service.Emojis;

public abstract class Dungeon {
    private String nameOfRoom;
    private String[][] dungeonMap;

    public Dungeon(String[][] dungeonMap, String nameOfRoom) {
        this.nameOfRoom = nameOfRoom;
        this.dungeonMap = dungeonMap;
    }

    public String[][] getDungeonMap() {
        return dungeonMap;
    }

    public abstract void description();
    public abstract void content();

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
                    case "P": emoji = Emojis.potion; break;
                    default: emoji = Emojis.path; break;
                }

                System.out.print(" "+emoji);
            }
            System.out.println();
        }
    }

}
