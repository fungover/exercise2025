package org.example.entities;

public class Boss extends Enemy {
    private final String specialMove;

    public Boss(String type, int health, int damage, int positionX, int positionY, String specialMove) {
        super(type, health, damage, positionX, positionY);
        this.specialMove = specialMove;
    }

    public String getSpecialMove() {
        return specialMove;
    }

    @Override
    public int getDamage() {
        return super.getDamage();
    }

    public void useSpecialMove(Player player) {
        int dmgAdd = 10;
        int totalDmg = getDamage() + dmgAdd;

        System.out.println(getType() + " used " + getSpecialMove() + " it dealt " + totalDmg + " damage");

        player.setHealth(player.getHealth() - totalDmg);
    }
}
