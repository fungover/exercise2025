package entities;

public class HealingPotion extends Item {

    public HealingPotion(String name, String type, int healingAmount) {
        super(name, type, healingAmount);
    }

    public void heal(Player player) {
        System.out.println(player.getName() + " drinks " + getName() + " and heals " + getEffect() + " hp");
        player.heal(getEffect());
    }
}
