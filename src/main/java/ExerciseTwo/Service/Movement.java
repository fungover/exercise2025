package ExerciseTwo.Service;

import java.util.Scanner;

public class Movement {

    private final Position position;

    public Movement(Position position) {
        this.position = position;
    }

    public String move(Scanner sc){
        while (true){
            System.out.println("In which direction do you want to move?");
            System.out.println("""
                Enter way to move:
                    s - south
                    n - north
                    e - east
                    w - west
                """);

            switch (sc.nextLine().toLowerCase()){
                case "n": return "north";
                case "s": return "south";
                case "e": return "east";
                case "w": return "west";
                default:
                    System.out.println("Wrong commando");
            }
        }
    }

    public String checkMovement(String[][] dungeonMap, String move) {

        int playerRow = position.getRow();
        int playerCol = position.getCol();

        if(move.equals("north")){
            playerRow--;
        }
        if(move.equals("south")){
            playerRow++;
        }
        if(move.equals("east")){
            playerCol++;
        }
        if(move.equals("west")){
            playerCol--;
        }

        String checkMap = dungeonMap[playerRow][playerCol];

        switch (checkMap) {
            case " ", "@" -> {
                position.setPosition(playerRow, playerCol);
                return "path";
                //or update map for @
            }
            case "E" -> {
                position.setPosition(playerRow, playerCol);
                return "enemy";
            }
            case "G" -> {
                position.setPosition(playerRow, playerCol);
                return "gift";
            }
            case "D" -> {
                position.setPosition(playerRow, playerCol);
                return "door";
            }
            default -> {
                System.out.println("You cant walk true walls, try again");
                return "wall";
            }
        }
    }
}
