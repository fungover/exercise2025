package dungeoncrawler.enteties;

public class Enemy extends Entity {
    int hp;
    int damage;
    int strength;
    int[] position;

    public Enemy(){
    }
    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage += damage;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int getStrength() {
        return strength;
    }
}
