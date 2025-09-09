package dev.ronja.dungeon.entities;

/**  My first public record, symbolic for what mothers do - They heal and comfort
 * This is immutable **/

public record Mother(int healAmount) implements Healer {
    public Mother {
        healAmount = Math.max(0, healAmount);
    }

    @Override
    public void heal(Player player) {
        player.heal(healAmount);
        System.out.println(" Mother comforts " + player.getName() + " and restores "
                + healAmount + " HP 💘");
    }
}
