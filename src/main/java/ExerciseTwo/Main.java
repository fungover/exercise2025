package ExerciseTwo;

import ExerciseTwo.Entities.*;
import ExerciseTwo.Game.InputHandling;
import ExerciseTwo.Map.Cave;
import ExerciseTwo.Map.Dungeon;
import ExerciseTwo.Map.Hallway;
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

        GameLogic gameLogic = new GameLogic();
        gameLogic.runGame(hallway, player);
        gameLogic.runGame(cave, player);

    }
}
