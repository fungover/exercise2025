package org.example;

import org.example.map.Tile;
import org.example.map.TileType;

public class App {
    public static void main(String[] args) {


        Tile wall = new Tile(TileType.WALL);
        Tile floor = new Tile(TileType.FLOOR);

        System.out.println(wall.getSymbol());
        System.out.println(wall.isWalkable());
        System.out.println(floor.getSymbol());
        System.out.println(floor.isWalkable());
    }
}
