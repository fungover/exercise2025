package org.example.entities;

public class Potion extends Item {
    private int healAmount;

    public Potion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Character character) {
        character.heal(healAmount);
        System.out.println(character.getName() + " healed for " + healAmount + " HP!");
    }
}
