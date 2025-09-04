package org.example.game.cli;

import java.util.Scanner;

public record CliCombatUI(Scanner in) implements CombatUI {

    @Override
    public void showIntro(String enemyName, int playerHp, int playerMaxHp, int enemyHp, int enemyMaxHp) {
        System.out.println("A " + enemyName + " jumps out of the shadows!");
        showStatus(playerHp, playerMaxHp, enemyHp, enemyMaxHp);
        showMessage("[Combat] Commands: a|attack, use <n>, f|flee, h|help");
    }

    @Override
    public void showStatus(int playerHp, int playerMaxHp, int enemyHp, int enemyMaxHp) {
        System.out.println("You " + playerHp + "/" + playerMaxHp + "   |   Enemy " + enemyHp + "/" + enemyMaxHp);
    }

    @Override
    public void playerAttackFeedback(String enemyName, int damage) {
        System.out.println("You hit the " + enemyName + " for " + damage + ".");
    }

    @Override
    public void enemyAttackFeedback(String enemyName, int damage) {
        System.out.println("The " + enemyName + " hits you for " + damage + ".");
    }

    @Override
    public void usedItemFeedback(String itemName, boolean success) {
        System.out.println(success ? ("Used: " + itemName) : "Can't use that now.");
    }

    @Override
    public void showMessage(String text) {
        System.out.println(text);
    }

    @Override
    public Action readAction() {
        System.out.print("(combat)> ");
        String line = in.nextLine().trim().toLowerCase();
        switch (line) {
            case "a", "attack" -> {
                return Action.attack();
            }
            case "f", "flee" -> {
                return Action.flee();
            }
            case "h", "help" -> {
                return Action.help();
            }
        }
        if (line.startsWith("use ")) {
            try {
                int idx = Integer.parseInt(line.substring(4).trim());
                if (idx > 0) return Action.use(idx);
            } catch (NumberFormatException ignored) {
            }
            return Action.unknown();
        }
        return Action.unknown();
    }
}
