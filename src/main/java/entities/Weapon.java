package entities;

/**
 * Weapon ändrar spelarens basdamage när den används.
 */
public class Weapon implements Item {
    private final String name;
    private final int bonusDamage;

    public Weapon(String name, int bonusDamage) {
        this.name = name;
        this.bonusDamage = bonusDamage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Player player) {
        if (player != null && player.isAlive()) {
            player.setBaseDamage(player.getBaseDamage() + bonusDamage);
        }
    }
}
