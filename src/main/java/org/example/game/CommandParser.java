package org.example.game;

import org.example.map.Dungeon;
import org.example.service.CombatService;
import org.example.service.ItemService;
import org.example.service.MovementService;

public class CommandParser {
    private final Dungeon dungeon;
    private final MovementService movementService;
    private final CombatService combatService;
    private final ItemService itemService;

    public CommandParser(Dungeon dungeon, MovementService movementService, CombatService combatService, ItemService itemService) {
        if (dungeon == null || movementService == null || combatService == null || itemService == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.dungeon = dungeon;
        this.movementService = movementService;
        this.combatService = combatService;
        this.itemService = itemService;
        System.out.println("CommandParser initialized for dungeon with dimensions " + dungeon.getWidth() + "x" + dungeon.getHeight());
    }

    public String parseCommand(String command) {
        if (command == null || command.trim().isEmpty()) {
            System.out.println("Invalid command: null or empty");
            return "Invalid command: null or empty";
        }
        System.out.println("Parsing command: " + command);
        String[] parts = command.trim().toLowerCase().split("\\s+");
        try {
            switch (parts[0]) {
                case "move":
                    if (parts.length != 2) {
                        System.out.println("Invalid move command: expected 'move <direction>'");
                        return "Invalid command: use 'move <direction>' (e.g, 'move up'";
                    }
                    return handleMoveCommand(parts[1]);
                case "attack":
                    if (parts.length != 3) {
                        System.out.println("Invalid attack command: expected 'attack <x> <y>'");
                        return "Invalid command: use 'attack <x> <y>' (e.g, 'attack 1 1')";
                    }
                    return handleAttackCommand(parts[1], parts[2]);
                case "pickup":
                    if (parts.length != 3) {
                        System.out.println("Invalid pickup command: expected 'pickup <x> <y>'");
                        return "Invalid command: use 'pickup <x> <y>' (e.g, 'pickup 2 2')";
                    }
                    return handlePickupCommand(parts[1], parts[2]);
                default:
                    System.out.println("Unknown command: " + parts[0]);
                    return "Unknown command: use move, attack, or pickup";
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid coordinates: " + e.getMessage());
            return "Invalid coordinates: use integers for x & y";
        } catch (IllegalArgumentException e) {
            System.out.println("Command failed: " + e.getMessage());
            return "Command failed: " + e.getMessage();
        }
    }

    private String handleMoveCommand(String direction) {
        int dx = 0, dy = 0;
        switch (direction.toLowerCase()) {
            case "up":
                dy = -1;
                break;
            case "down":
                dy = 1;
                break;
            case "left":
                dx = -1;
                break;
            case "right":
                dx = 1;
                break;
            default:
                System.out.println("Invalid direction: " + direction);
                return "Invalid direction: use 'up', 'down', 'left', or 'right'";
        }
        movementService.movePlayer(dx, dy, dungeon.getPlayer());
        return "Player moved " + direction;
    }

    private String handleAttackCommand(String xString, String yString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        combatService.engageCombat(x, y, dungeon.getPlayer());
        return "Attacked at (" + x + ", " + y + ")";
    }

    private String handlePickupCommand(String xString, String yString) {
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        itemService.pickUpItem(x, y, dungeon.getPlayer());
        return "Picked up item at (" + x + ", " + y + ")";
    }
}
