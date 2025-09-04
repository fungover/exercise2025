package ExerciseTwo;

import ExerciseTwo.Entities.*;
import ExerciseTwo.Game.InputHandling;
import ExerciseTwo.Map.*;
import ExerciseTwo.Service.*;

import java.util.Scanner;

public class Main {

    static void main() {
        Scanner sc = new Scanner(System.in);

        InputHandling inputHandling = new InputHandling();
        String name = inputHandling.enterName(sc);
        Player player = new Player(name);
        player.presentPlayer();

        InputHandling.commands();

        Dungeon hallway = new Hallway();
        Dungeon cave = new Cave();
        Dungeon claustrophobic = new Claustrophobic();
        Dungeon treasure = new Treasue();

        GameLogic gameLogic = new GameLogic();
        gameLogic.runGame(hallway, player);
        gameLogic.runGame(cave, player);
        gameLogic.runGame(claustrophobic, player);
        gameLogic.runGame(treasure, player);

    }
}
