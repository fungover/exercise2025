package org.example.service;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Tile;

//To-do
//Method to pick up items
//Method to use items
//Method to print inventory line

public class ItemService {

    public static void pickupItem(Tile[][] tiles, Player player){
        int x = player.getX();
        int y = player.getY();
        int rows = tiles.length;
        int cols = tiles[0].length;

        if(tiles[x][y].getItem() == null){
            System.out.println("Nothing to pickup here.");
            return;
        }

        if (tiles[x][y].getItem().getType().equals("map")){
            for (Tile[] tile : tiles) {
                for (int j = 0; j < cols; j++) {
                    tile[j].setVisited(true);
                }
            }
            System.out.println("You have picked up a map! Revealing the whole dungeon");
            tiles[x][y].setItem(null);

        } else if(tiles[x][y].getItem().getType().equals("potion")){
            player.addItem(tiles[x][y].getItem());
            System.out.println("You have picked up a potion!");
            tiles[x][y].setItem(null);
        }
    }

    public static void useItem(Player player){
        Item potion = null;

        for (Item item : player.getInventory()){
            if (item.getType().equals("potion")){
                potion = item;
                break;
            }
        }

        if (potion == null){
            System.out.println("You don't have potions to use!");
            return;
        }

        int healAmount = potion.getEffect();
        int newHealth = Math.min(100, player.getHealth() + healAmount);
        int healedFor = newHealth - player.getHealth();

        player.setHealth(newHealth);
        player.getInventory().remove(potion);

        System.out.println("You have been healed for " + healedFor + " HP! Current health: " + player.getHealth());
    }

    public static void printInventory(Player player){

        int potionCount = 0;

        for (Item item : player.getInventory()) {
            if (item.getType().equals("potion")) {
                potionCount++;
            }
        }

        if (potionCount == 0) {
            System.out.println("Inventory: Empty! Commands: [pickup] = pick up item");
        } else {
            System.out.println("Inventory: [" + potionCount + "] " + (potionCount == 1 ? "Potion! Commands: [pickup] [use potion]" : "Potions! Commands: [pickup] [use potion]"));
        }
    }
}