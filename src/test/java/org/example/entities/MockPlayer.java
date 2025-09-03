package org.example.entities;

//used for mocking testing
public class MockPlayer extends Player {
//    private int x;
//    private int y;
    //    private int baseDamage;
    //    private int weaponDamage;


    public MockPlayer() {
        super("MockPlayer");


    }

    public MockPlayer(int x, int y) {
        super("MockPlayer");
        super.setPosition(x, y);
    }


}
