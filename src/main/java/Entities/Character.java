package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Character {
    private final UUID id;
    protected String type;
    protected int health;
    protected int attack;
    protected int x;
    protected int y;

    private static final List<Character> CHARACTERS = new ArrayList<>();

    public Character(String type, int health, int attack, int x, int y) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.x = x;
        this.y = y;
        CHARACTERS.add(this);
    }
}