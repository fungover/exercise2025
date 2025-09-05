package entities;

public class Key implements Item {
    private final String name;

    public Key(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Player player) {
        // Kan utökas för att öppna dörrar senare
        System.out.println("You used the " + name + ". Nothing happened... yet.");
    }
}
