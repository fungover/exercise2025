package ExerciseTwo.Game;

import ExerciseTwo.Entities.Inventory.Inventory;
import ExerciseTwo.Entities.Player;
import ExerciseTwo.Entities.Weapons.Weapon;
import ExerciseTwo.Service.HandleInventory;
import ExerciseTwo.Service.HandleWeapon;

import java.util.Scanner;

public class PlayerInput {

    Inventory inventory;
    Player player;
    Weapon weapon;
    Scanner sc;

    public PlayerInput(Inventory inventory, Player player, Weapon weapon, Scanner sc) {
        this.inventory = inventory;
        this.player = player;
        this.weapon = weapon;
        this.sc = sc;
    }

    public boolean commandInput(String inputFromPlayer) {

        switch (inputFromPlayer) {
            case "i" : HandleInventory.handleInventory(inventory, player, sc); return true;
            case "j" : HandleWeapon.handleWeapon(weapon, player); return true;
            case "h":  InputHandling.commands(); return true;
            case "p" : player.playerHealth(); return true;
            case "quit": System.exit(0);
            default: return false;
        }
    }
}
