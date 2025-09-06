package game;

import map.DungeonMap;

public class Main {
    public static void main(String[] args) {
        DungeonMap dungeonMap = new DungeonMap(50, 25);
        dungeonMap.generate(10, 4, 8);
        dungeonMap.printToConsole();
    }
}
