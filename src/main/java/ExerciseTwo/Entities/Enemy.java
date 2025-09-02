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

    //public void position();
    public abstract void description();

    public  void damage(int damage) {
        health -= damage;
    }

    public int getAttack(){
        return attack;
    }
}
