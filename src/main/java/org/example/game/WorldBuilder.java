package org.example.game;

import org.example.entities.*;

public class WorldBuilder {

    public static Room createWorld() {
        // Create rooms
        Room startCave = new Room("Dark Cave",
                "The cave where you woke up. Cold stone walls surround you. \nThade's voice still echoes here.");

        Room weaponChamber = new Room("Weapon Chamber",
                "An old armory. Rusty weapons hang on the walls.");

        Room prisonDungeon = new Room("Prison",
                "Two prison cells, one with a red door (north) and one with a blue door (south).");

        Room redPrisonCell = new Room("Red Prison Cell",
                "This is someone's home..");

        Room bluePrisonCell = new Room("Blue Prison Cell",
                "It's spotless in here.. and a family photo on the wall.");

        Room corridor = new Room("Stone Corridor",
                "A long, narrow corridor. You hear growling in the distance.");

        Room trollLair = new Room("Troll's Lair",
                "A massive chamber. The exit door is here! \nYou could try and unlock it..");

        // Connect rooms
        connectRooms(startCave, weaponChamber, prisonDungeon, redPrisonCell,
                bluePrisonCell, corridor, trollLair);

        // Add items to rooms
        addItemsToRooms(startCave, weaponChamber, bluePrisonCell);

        // Add enemies to rooms
        addEnemiesToRooms(weaponChamber, corridor, trollLair, bluePrisonCell);

        return startCave; // Return starting room
    }

    private static void connectRooms(Room startCave, Room weaponChamber, Room prisonDungeon,
                                     Room redPrisonCell, Room bluePrisonCell, Room corridor, Room trollLair) {
        startCave.addExit("east", weaponChamber);
        startCave.addExit("north", corridor);

        weaponChamber.addExit("west", startCave);
        weaponChamber.addExit("east", prisonDungeon);

        prisonDungeon.addExit("north", redPrisonCell);
        prisonDungeon.addExit("south", bluePrisonCell);
        prisonDungeon.addExit("west", weaponChamber);

        redPrisonCell.addExit("south", prisonDungeon);
        bluePrisonCell.addExit("north", prisonDungeon);

        corridor.addExit("south", startCave);
        corridor.addExit("north", trollLair);

        trollLair.addExit("south", corridor);
    }

    private static void addItemsToRooms(Room startCave, Room weaponChamber, Room bluePrisonCell) {
        Weapon sword = new Weapon("Sword", "An rusty old sword", "Weapon", 1, 15);
        Healing potion = new Healing("Healing Potion", "A red liquid that smells of herbs", "Healing", 1, 30);
        Healing herb = new Healing("Healing Herb", "A green leaf with a cross on it", "Healing", 1, 30);
        Key goldenKey = new Key("Golden Key", "This golden key can open any locked door", "Key", 1);

        weaponChamber.addItem(sword);
        startCave.addItem(potion);
        bluePrisonCell.addItem(herb);
        bluePrisonCell.addItem(goldenKey);
    }

    private static void addEnemiesToRooms(Room weaponChamber, Room corridor, Room trollLair, Room bluePrisonCell) {
        Enemy bat = new Enemy("Bat", "A small and aggressive creature", 20, 5);
        Enemy goblin = new Enemy("Cave Goblin", "A small but vicious creature", 30, 10);
        Enemy troll = new Enemy("Giant Troll", "The massive guardian of the exit", 60, 20);
        Enemy thade = new Enemy("Thade", "Just a dark human shape", 50, 30);

        weaponChamber.addEnemy(bat);
        corridor.addEnemy(goblin);
        trollLair.addEnemy(troll);
        bluePrisonCell.addEnemy(thade);
    }
}