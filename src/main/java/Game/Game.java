package Game;

import Entities.*;
import Map.DungeonMap;
import Service.CommandParser;
import Service.CombatService;
import Service.MovementService;

import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);


        Player player = new Player("Hero", 30, 10, 0, 0);


        DungeonMap map = new DungeonMap(10, 10);
        map.generateBasicLayout();
        map.placePlayerAtStart(player);


        map.populateRandom(1, 0.35, 10, 10);


        MovementService movement = new MovementService(map);
        CombatService combat = new CombatService(map);
        CommandParser parser = new CommandParser();

        System.out.println("Welcome to the Dungeon!");
        parser.printHelp();
        boolean running = true;
        while (running) {
            System.out.print("> ");
            String line = in.nextLine().trim();
            switch (parser.parse(line)) {
                case MOVE_N -> movement.move(player, 0, -1);
                case MOVE_S -> movement.move(player, 0, 1);
                case MOVE_W -> movement.move(player, -1, 0);
                case MOVE_E -> movement.move(player, 1, 0);
                case LOOK -> map.describeCurrentTile(player);
                case ATTACK -> combat.attackCurrentTile(player);
                case INVENTORY -> player.printInventory();
                case USE_POTION -> player.useFirstPotion();
                case HELP -> parser.printHelp();
                case QUIT -> running = false;
                case INVALID -> System.out.println("Unknown command. Type 'help'.");
            }
            if (!player.isAlive()) {
                System.out.println("You died! Game over.");
                running = false;
            }
        }
        System.out.println("Bye!");
    }
}
