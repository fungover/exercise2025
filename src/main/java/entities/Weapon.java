package entities;

/**
 * Weapon ändrar spelarens basdamage när den används.
 */
public class Weapon implements Item {
    private final String name;
    private final int bonusDamage;
    private boolean applied = false;

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
        if (player != null && player.isAlive() && !applied && bonusDamage > 0) {
            player.setBaseDamage(player.getBaseDamage() + bonusDamage);
            applied = true;
        }
    }
}
