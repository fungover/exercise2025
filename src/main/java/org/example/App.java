package org.example;

import org.example.map.Dungeon;

public class App {
    public static void main(String[] args) {


        Dungeon dungeon = new Dungeon(10, 14);
        dungeon.printMap();
    }
}
