package entities;

public class Weapon extends Item{
    private int bonusDamage;

    public Weapon(String name, int bonusDamage) {
        super(name);
        this.bonusDamage = bonusDamage;

    }
    
    @Override
    public void use(Player player) {
        player.increaseDamage(bonusDamage);
        System.out.println("You picked up " + getName() + "!");
        System.out.println("Your damage increased by " + bonusDamage + ". Current damage: " + player.getDamage());
    }
}
