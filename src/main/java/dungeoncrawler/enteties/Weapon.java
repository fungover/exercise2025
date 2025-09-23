package dungeoncrawler.enteties;

public class Weapon extends Entity {
    String type;
    int strength;

    public Weapon(String type, int strength, int[] position) {
        this.type = type;
        this.strength = strength;
        this.position = position;
    }
}
