package org.example.entities;

//used for mocking testing
public class MockPlayer extends Player {
    private int x;
    private int y;
    //    private int baseDamage;
    //    private int weaponDamage;


    public MockPlayer() {
        super("MockPlayer");
        //        this.baseDamage = 10;


    }

    public MockPlayer(int x, int y) {
        super("MockPlayer");
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    //    public int getDamage() {return baseDamage + weaponDamage;}


}
