package ExerciseTwo.Entities;

public abstract class Enemy {
    protected String type;
    protected int health;
    protected int attack;

    public Enemy(String type, int health, int attack){
        this.type = type;
        this.health = health;
        this.attack = attack;
    }

    public abstract void description();

    public  void setHealth(int damage) {
        health += damage;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack(){
        return attack;
    }
}
