package ExerciseTwo.Service;

import ExerciseTwo.Game.PlayerInput;
import ExerciseTwo.Utils.PrintText;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

import java.util.Scanner;

public record Movement(Position position) {

    public String move(Scanner sc, PlayerInput playerInput) {
        while (true) {
            PrintText.printBlue("In which direction do you want to move?");
            System.out.println("""
                    Enter way to move:
                        s - south
                        n - north
                        e - east
                        w - west
                    """);

            String inputFromPlayer = sc.nextLine().toLowerCase();

            if(playerInput.commandInput(inputFromPlayer)){
                continue;
            }

            switch (inputFromPlayer) {
                case "n":
                    return "north";
                case "s":
                    return "south";
                case "e":
                    return "east";
                case "w":
                    return "west";
                default:
                    System.out.println("Wrong commando");
            }
        }
    }

    public String checkMovement(String[][] dungeonMap, String move) {

        int newRow = position.getRow();
        int newCol = position.getCol();

        if (move.equals("north")) {
            newRow--;
        }
        if (move.equals("south")) {
            newRow++;
        }
        if (move.equals("east")) {
            newCol++;
        }
        if (move.equals("west")) {
            newCol--;
        }

        if(newRow < 0 || newRow >= dungeonMap.length || newCol < 0 || newCol >= dungeonMap[newRow].length){
            System.out.println("You cant move outside of the map");
            return "wall";
        }

        String checkMap = dungeonMap[newRow][newCol];

        switch (checkMap) {
            case " ", "@" -> {
                position.setPosition(newRow, newCol);
                return "path";
                //or update map for @
            }
            case "P" -> {
                position.setPosition(newRow, newCol);
                return "potion";
            }
            case "E" -> {
                position.setPosition(newRow, newCol);
                return "enemy";
            }
            case "G" -> {
                position.setPosition(newRow, newCol);
                return "coin";
            }
            case "S" -> {
                position.setPosition(newRow, newCol);
                return "sword";
            }
            case "D" -> {
                position.setPosition(newRow, newCol);
                return "door";
            }
            case "T" -> {
                PrintText.printBlue("You found the secret treasure "+Emojis.treasure);
                PrintText.printBlue("You WON! CONGRATULATIONS!");
                return "treasure";
            }
            default -> {
                System.out.println("You cant walk true walls, try again");
                return "wall";
            }
        }
    }
}
