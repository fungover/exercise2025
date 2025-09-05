package dungeoncrawler.enteties;

public class Player {
    String name;
    int hp;
    int totalStrength;
    int weaponStrength;
    int damage;
    int[] position;
    int[] previousPosition;

    public Player(String name, int hp, int[] position) {
        this.name = name;
        this.hp = hp;
        this.position = position;
        previousPosition = position;
        totalStrength = 10;
        weaponStrength = 0;
    }

    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setDamage(int damage) {
        this.damage += damage;
    }
    public int getDamage() {
        return damage;
    }
    public int[] getPosition() {
        return position;
    }
    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getPreviousPosition() {
        return previousPosition;
    }
    public void setPreviousPosition(int[] prevPos) {
        this.previousPosition = prevPos;
    }

    public int getTotalStrength() {
        return totalStrength;
    }

    public void setTotalStrength(int totalStrength) {
        this.totalStrength = totalStrength;
    }

    public int getWeaponStrength() {
        return weaponStrength;
    }

    public void setWeaponStrength(int weaponStrength) {
        this.weaponStrength = weaponStrength;
    }
}


