package org.example.game;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.entities.Position;
import org.example.entities.Troll;

public class Game {
        public static void main(String[] args) {
            Position start = new Position(0, 0);
            Player hero = new Player("Hero", 20, 5, start);
            Enemy troll = new Troll(new Position(1, 1));

            hero.attack(troll);
            troll.takeDamage(3);
            System.out.println("Troll HP: " + troll.getHp());
        }
    }
