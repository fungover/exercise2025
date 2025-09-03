package org.example.service;

import org.example.entities.characters.Player;
import org.example.entities.items.Item;
import org.example.entities.items.Potion;
import org.example.entities.items.Weapon;
import org.example.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    public void interactWithItems(Player player, List<Item> items, InputService inputService) {
        System.out.println("You found " + items.size() + " item:");

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            boolean validInput = false;

            while (!validInput) {
                if (item instanceof Weapon) {
                    System.out.println("Name: " + item.getName());
                    System.out.println("Description: " + item.getDescription());
                    System.out.println("Damage: " + ((Weapon) item).getMinDamage() + " - " + ((Weapon) item).getMaxDamage());
                }

                System.out.println("What would you like to do with " + item.getName() + "?");
                if (item instanceof Weapon) {
                    System.out.println("1. Equip item");
                } else {
                    System.out.println("1. Use item");
                }
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

                        }

                        if (item instanceof Potion) {
                            player.usePotion((Potion) item);
                        }

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

    public void openInventoryMenu(Player player, InputService inputService) {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("What would you like to do with your inventory?");
            System.out.println("1. Equip weapon from inventory");
            System.out.println("2. Use potion");
            System.out.println("3. Quit");

            String input = inputService.readLine();

            if (!input.equals("1") && !input.equals("2")) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            switch (input) {
                case "1":
                    displayInventory(player, inputService);
                    validInput = true;
                    break;

                case "2":
                    Potion potion = player.getInventory().getFirstPotion();
                    if (potion != null) {
                        player.usePotion(potion);
                        Utils.newRow();
                    } else {
                        System.out.println("You don't have any health potions.");
                        Utils.newRow();
                        continue;
                    }
                    break;

                case "3":
                    validInput = true;
                    break;
            }

        }
    }

    public void displayInventory(Player player, InputService inputService) {
        List<Item> items = player.getInventory().getItems();
        List<Weapon> weapons = new ArrayList<>();

        for (Item item : items) {
            if (item instanceof Weapon) {
                weapons.add((Weapon) item);
            }
        }

        if (weapons.isEmpty()) {
            System.out.println("You don't have any weapons to equip.");
            return;
        }

        System.out.println("Which weapon do you want to equip?");
        boolean validInput = false;

        while (!validInput) {
            for (int i = 0; i < weapons.size(); i++) {
                System.out.println((i + 1) + ". " + weapons.get(i).getName());
                System.out.println("Description: " + weapons.get(i).getDescription());
                System.out.println("Damage: " + weapons.get(i).getMinDamage() + " - " + weapons.get(i).getMaxDamage()  + "\n");

            }

            String input = inputService.readLine();

            try {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= weapons.size()) {
                    Weapon selectedWeapon = weapons.get(choice - 1);
                    Weapon oldWeapon = player.getEquippedWeapon();

                    player.getInventory().addItem(oldWeapon);
                    player.setEquippedWeapon(selectedWeapon);
                    player.getInventory().removeItem(selectedWeapon);

                    System.out.println("You equipped " + selectedWeapon.getName());

                    validInput = true;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
