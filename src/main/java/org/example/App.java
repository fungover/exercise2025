package org.example;

import org.example.entities.Position;

public class App {
    public static void main(String[] args) {


        Position start = new Position(5, 5);
        System.out.println("Start Position: " + start);

        Position north = start.moveNorth();
        System.out.println("North Position: " + north);

        Position east = start.moveEast();
        System.out.println("East Position: " + east);

        Position same = new Position(5, 6);
        System.out.println("start.equals(same): " + start.equals(same));
    }
}
