package org.example.entities;

interface Health {
    void setHealth(int health);
    void increaseHealth(int amount);
    void decreaseHealth(int amount);
    int getHealth();
}
