package org.game.world;

import org.game.entities.Enemy;
import org.game.entities.Item;
import org.game.entities.npcs.Goblin;
import org.game.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    private String type;
    private boolean visited;
    private List<Enemy> enemies;
    private List<Item> loot;

    public Room(String type) {
        this.type = type;
        this.visited = false;
        this.enemies = new ArrayList<>();
        this.loot = new ArrayList<>();

        generateContents();
    }

    private void generateContents() {

        switch (type.toLowerCase()) {
            case "enemy":
                Goblin goblin = new Goblin(0,0);
                enemies.add(goblin);
                break;

            case "treasure":
                int Roll = RandomGenerator.randomInt(0,5);
                if (Roll == 0){
                    System.out.println("The chest is empty");
                }
                loot.add(new Item("coins","currency",1,RandomGenerator.randomInt(0,10)));
                loot.add(new Item("bread","consumable",1,1));

                break;
            case "boss":
                Goblin bossgoblin = new Goblin(0,0);
                enemies.add(bossgoblin);
                break;
            case "empty":
                break;
            case "shop":
                break;
            default:
                break;
        }
    }


    public String getType() {return type;}
    public List<Enemy> getEnemies(){return enemies;}
    public List<Item> getLoot(){return loot;}

    public void setVisited(boolean visited){this.visited=visited;}

    public boolean IsVisited(){return visited;}

}
