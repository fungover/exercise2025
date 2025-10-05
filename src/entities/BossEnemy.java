package entities;

public class BossEnemy extends Enemy {
    public BossEnemy() {
        super("Boss", 15, 3);
    }

    @Override
    public String getName() {
        return "Beast";
    }
}


