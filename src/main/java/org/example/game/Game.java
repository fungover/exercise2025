package org.example.game;

import org.example.entities.Position;

public class Game {
        public static void main(String[] args) {
            Position start = new Position(2, 3);
            System.out.println("Start position: " + start);

            Position moved1 = start.translate(1, 0);
            System.out.println("Moved east: " + moved1);

            Position moved2 = start.translate(0, -1);
            System.out.println("Moved north: " + moved2);

            Position another = new Position(3, 2);
            System.out.println("Is moved2 equal to another? " + moved2.equals(another));

            System.out.println("Hashcode of start: " + start.hashCode());
            System.out.println("Hashcode of another: " + another.hashCode());
        }
    }
