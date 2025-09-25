package org.game.entities;

import org.game.utils.Colors;
import org.game.utils.RandomGenerator;

public abstract class Character {
    protected String name;
    protected int health;
    protected int strength;
    protected int dexterity;
    protected int intelligence;
    protected int wisdom;
    protected int charisma;
    protected int defense;
    protected int x;
    protected int y;

    public Character(
        String name,
        int health,
        int strength,
        int dexterity,
        int intelligence,
        int wisdom,
        int charisma,
        int defense,
        int x,
        int y)
    {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.defense =defense;
        this.x = x;
        this.y = y;
    }

    public int takeDamage(int damage,boolean isEnemyAttack) {
        this.health -= damage;
        String damageColor = isEnemyAttack ? Colors.redColor : Colors.greenColor;
        System.out.println(damageColor+getName() + "takes " + damage + " damage! " + health + " health remaining!"+Colors.resetColor );
    return health;
    }

    public boolean attackDamage(Character target, boolean isPlayer) {
        int attackRoll = RandomGenerator.randomInt(0,this.strength);
        int defenceRoll = RandomGenerator.randomInt(0,target.dexterity + target.defense);
        //System.out.println(getName() + " rolled " +attackRoll + " attack");
        //System.out.println(getName() + " rolled " +defenceRoll + " defence");
        int damage = RandomGenerator.randomInt(1, this.dexterity + this.strength/10);
        if (attackRoll > RandomGenerator.randomInt(0,defenceRoll) && damage > 0) {


            target.takeDamage(damage,!isPlayer);
            return true;
            //System.out.println(Colors.greenColor+getName() + " attacks for " + damage + " damage!"+Colors.resetColor );
        }
        else {
            System.out.println(Colors.yellowColor+getName() + " missed " + target.getName() +"!"+Colors.resetColor);
        return false;
        }
    }

    //Todo Alive
    public boolean isAlive() { return this.health > 0;}

    //Todo Get values
    public String getName() {return this.name;}
    public int getHealth() { return this.health; }
    public int getStrength() { return this.strength; }
    public int getDexterity() { return this.dexterity; }
    public int getIntelligence() { return this.intelligence; }
    public int getWisdom() { return this.wisdom; }
    public int getCharisma() { return this.charisma; }
    public int getDefense() { return this.defense; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }

    //Todo Set values
    public void setPosition(int x, int y) {this.x = x; this.y = y;}
    public void setHealth(int health) { this.health = health; }
    public void setStrength(int strength) { this.strength = strength; }
    public void setDexterity(int dexterity) { this.dexterity = dexterity; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }
    public void setWisdom(int wisdom) { this.wisdom = wisdom; }
    public void setCharisma(int charisma) { this.charisma = charisma; }
    public void setDefense(int defense) { this.defense = defense; }
}
