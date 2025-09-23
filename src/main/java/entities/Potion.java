package entities;

public class Potion extends Item {
    private int healAmount;

    public Potion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }

    public int heal() {
        return healAmount;
    }

    @Override
    public String toString() {
        return "I";
    }

    @Override
    public void use(Player player) {
        player.setHealth(player.getHealth() + healAmount);
        System.out.println("You healed " + healAmount + " hp");
        System.out.println("Current health: " + player.getHealth() + " hp");
    }
}
