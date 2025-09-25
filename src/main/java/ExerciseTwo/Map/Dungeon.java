package ExerciseTwo.Map;

import ExerciseTwo.Service.Emojis;

public abstract class Dungeon {
    private String[][] dungeonMap;

    public Dungeon(String[][] dungeonMap) {
        if (!checkMap(dungeonMap) || !validateMap(dungeonMap)) {
            throw new IllegalArgumentException("Invalid map");
        }
        this.dungeonMap = dungeonMap;
    }

    private boolean checkMap(String[][] dungeonMap) {
        if (dungeonMap == null) {
            return false;
        }
        if(dungeonMap.length == 0 || dungeonMap[0].length == 0) {
            return false;
        }
        return true;
    }

    private boolean validateMap(String[][] dungeonMap) {
        for (int i = 0; i < dungeonMap.length; i++) {
            for (int j = 0; j < dungeonMap[0].length; j++) {
                if (!dungeonMap[0][j].equals("#")) {
                    return false;
                }
                if (!dungeonMap[i][0].equals("#")) {
                    return false;
                }
                String[] row = dungeonMap[i];

                if(!row[row.length-1].equals("#")) {
                    return false;
                }
            }
        }
        return true;
    }

    public String[][] getDungeonMap() {
        return dungeonMap;
    }

    public abstract void description();

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
                    case "G": emoji = Emojis.coin; break;
                    case "P": emoji = Emojis.potion; break;
                    case "S": emoji = Emojis.sword; break;
                    case "T": emoji = Emojis.treasure; break;
                    default: emoji = Emojis.path; break;
                }

                System.out.print(" "+emoji);
            }
            System.out.println();
        }
    }

}
