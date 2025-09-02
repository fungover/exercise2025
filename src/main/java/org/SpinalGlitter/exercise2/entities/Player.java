package org.SpinalGlitter.exercise2.entities;

public class Player {

    private String name;
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private Position position = new Position(0, 0);
    private Inventory inventory = new Inventory(20);

    public Player (String name) {
        this.name = name;
        this.maxHealth = 100;
        this.damage = 10;
        this.currentHealth = 90;
        this.position = new Position(1, 1);

    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return  name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void takeDamage(int amount) {
        currentHealth -= amount;
        if(currentHealth <= 0) {
            currentHealth = 0;
        }

    }
        public boolean isAlive() {
            return currentHealth > 0;
        }

        public Position getPosition() {
            return position;
        }

        public void move(int dx, int dy) {
            this.position = position.getAdjacent(dx, dy);
        }

         public void heal(int amount) {
            if(!inventory.hasPotion()) {
                System.out.println("No items in inventory to heal.");
                return;
            } else if (currentHealth == maxHealth) {
                    System.out.println("Health is already full.");
                    return;
                } else  {
                    inventory.consumeFirstPotion();
                    currentHealth += amount;
                    if (currentHealth > maxHealth) {
                        currentHealth = maxHealth;
                    }
                }
             }
         }



    // take damage method
    // heal method
    //pick up item method
    // drop item method
    // use item method
    // get inventory method
    // attack method
    // move location method
    // take damage method
    //

