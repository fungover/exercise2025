package org.example.service;

import org.example.entities.Player;
import org.example.entities.items.Item;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PotionLogic {
    private Scanner scanner = new Scanner(System.in);

    public void usePotion(Player player) {
        List<Item> potions = player.getInventory().stream()
                .filter(i -> i.getType().equals("potion"))
                .collect(Collectors.toList());

        if (potions.isEmpty()) {
            System.out.println("You have no potions!");
            return;
        }

        System.out.println("You have the following potions:");
        for (int i = 0; i < potions.size(); i++) {
            Item p = potions.get(i);
            System.out.println((i + 1) + ") " + p.getName() + " (" + p.getEffect() + " heal)");
        }

        int choice = -1;
        while (choice < 1 || choice > potions.size()) {
            System.out.print("Which potion do you want to use? ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        Item potion = potions.get(choice - 1);
        player.setHealth(Math.min(player.getHealth() + potion.getEffect(), 100));
        player.getInventory().remove(potion);

        System.out.println("You used a " + potion.getName() + " and healed " + potion.getEffect() + "!");
        System.out.println("Your health is now " + player.getHealth() + ".");
    }
}
