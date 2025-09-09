package org.game.world;

import org.game.entities.Enemy;
import org.game.entities.Item;
import org.game.entities.npcs.Dragon;
import org.game.entities.npcs.Goblin;
import org.game.entities.npcs.Skeleton;
import org.game.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

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
                Skeleton skeleton = new Skeleton(0,0);
                int rollEnemy = RandomGenerator.randomInt(0,1);
                if (rollEnemy == 0){enemies.add(goblin);}
                else if (rollEnemy == 1) {enemies.add(skeleton);}
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
                Dragon dragon = new Dragon(0,0);
                enemies.add(dragon);
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
