package org.example.service;

import org.example.entities.characters.Player;
import org.example.entities.items.Item;
import org.example.entities.items.Weapon;

import java.util.List;

public class ItemService {
    public void interactWithItems(Player player, List<Item> items, InputService inputService) {
        System.out.println("You found " + items.size() + " items:");

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            boolean validInput = false;

            while (!validInput) {
                System.out.println("What would you like to do with " + item.getName() + "?");
                System.out.println("1. Equip item");
                System.out.println("2. Add item to inventory");

                String input = inputService.readLine();

                if (!input.equals("1") && !input.equals("2")) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        if (item instanceof Weapon weapon) {
                            Weapon oldWeapon = player.getEquippedWeapon();
                            player.getInventory().addItem(oldWeapon);

                            player.setEquippedWeapon(weapon);
                            System.out.println("You equipped " + weapon.getName() + " and added " + oldWeapon.getName() + " to your inventory.");

                        } else {
                            System.out.println("You can't equip that item!");
                        }

                        //Add potion logic here

                        validInput = true;
                        break;
                    case 2:
                        player.getInventory().addItem(item);
                        System.out.println(item.getName() + " added to inventory!");
                        validInput = true;
                        break;
                }
            }
        }
    }
}
