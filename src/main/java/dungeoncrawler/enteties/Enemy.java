package dungeoncrawler.enteties;

public class Enemy extends Entity {
    private int hp;
    private int strength;

    public Enemy(){
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }
}
