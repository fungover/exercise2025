package dev.ronja.dungeon.entities;

public record Potion(int healAmount) implements Healer {
    public Potion {
        healAmount = Math.max(0, healAmount);
    }

    @Override
    public void heal(Player player) {
        player.heal(healAmount);
        System.out.println(" You drink a Potion and restore " + healAmount + " in HP 🌟 ");

    }

}
