package dungeoncrawler.enteties;

public class Enemy extends Entity {
    int hp;
    int damage;
    int strength;

    public Enemy(){
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage += damage;
    }


    public int getStrength() {
        return strength;
    }
}
