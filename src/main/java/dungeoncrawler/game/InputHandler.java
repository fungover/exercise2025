package dungeoncrawler.game;

import dungeoncrawler.utils.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    Scanner scanner;
    Message message;
    List<String> validMovementInput;
    List<String> validCombatInput;
    List<String> validYesNoInput;

    public InputHandler() {
        scanner = new Scanner(System.in);
        message = new Message();
        validMovementInput = new ArrayList<>();
        validCombatInput = new ArrayList<>();
        validYesNoInput = new ArrayList<>();
        validMovementInput.add("go north");
        validMovementInput.add("go east");
        validMovementInput.add("go west");
        validMovementInput.add("go south");
        validCombatInput.add("hide");
        validCombatInput.add("fight");
        validCombatInput.add("run");
        validCombatInput.add("look");
        validYesNoInput.add("yes");
        validYesNoInput.add("no");
    }
    public String handleMovementInput(){
        String input = scanner.nextLine();
        while(true) {
            if (validMovementInput.contains(input.toLowerCase())) {
                return input;
            } else {
                message.send("Invalid movement option.");
                input = scanner.nextLine();
            }
        }
    }
    public String handleInput(){
        return scanner.nextLine();
    }
    public String handleCombatInput(){
        String input = scanner.nextLine();
        while(true) {
            if (validCombatInput.contains(input.toLowerCase())) {
                return input;
            } else {
                message.send("Invalid combat option.");
                input = scanner.nextLine();
            }
        }
    }
    public String handleYesNoInput(){
        String input = scanner.nextLine();
        while(true) {
            if (validYesNoInput.contains(input.toLowerCase())) {
                return input;
            } else {
                message.send("Invalid input option.");
                input = scanner.nextLine();
            }
        }
    }

}
