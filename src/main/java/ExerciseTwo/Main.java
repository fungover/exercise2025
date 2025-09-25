package ExerciseTwo;

import ExerciseTwo.Entities.*;
import ExerciseTwo.Game.GameLogic;
import ExerciseTwo.Game.InputHandling;
import ExerciseTwo.Map.*;

import java.util.Scanner;

public class Main {

    static void main() {
        Scanner sc = new Scanner(System.in);

        InputHandling inputHandling = new InputHandling();
        String name = inputHandling.enterName(sc);

        Player player = new Player(name);
        player.presentPlayer();

        InputHandling.commands();

        GameLogic gameLogic = new GameLogic(player);

        Dungeon hallway = new Hallway();
        Dungeon cave = new Cave();
        Dungeon claustrophobic = new Claustrophobic();
        Dungeon treasure = new Treasue();

        gameLogic.gameLoop(hallway);
        gameLogic.gameLoop(cave);
        gameLogic.gameLoop(claustrophobic);
        gameLogic.gameLoop(treasure);

    }
}
