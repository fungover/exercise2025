package dungeoncrawler.map;

import dungeoncrawler.enteties.*;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    Tile [][] tiles;
    List<Enemy> enemies;
    List<Potion> potions;
    List<Weapon> weapons;
    List<Entity> items;

    public Dungeon() {
        items = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        potions = new ArrayList<Potion>();
        weapons = new ArrayList<Weapon>();
        tiles = new Tile[30][15];
        placeWalls();
    }
    public void placeEntity(String type, int[] pos){
        if(pos[0] < 0 || pos[0] >= tiles.length || pos[1] < 0 || pos[1] >= tiles[0].length){
            throw new IllegalArgumentException("Position out of bounds. [" + pos[0] + ", " + pos[1] + "]");
        }
        Tile tile = new Tile(type);
        tile.setPosition(pos);
        tiles[pos[0]][pos[1]] = tile;
    }
    private void placeWalls(){
        Tile wall = new Tile("Wall");
        for(int i=0; i<tiles.length; i++){
            for(int j=0; j<tiles[i].length; j++){
                if(j == 0 || j == 14 || i == 0 || i == 29){
                    tiles[i][j] = wall;
                }if(j == 1 && i == 11){
                    tiles[i][j] = wall;
                }if(j == 2 && i==17){
                    tiles[i][j] = wall;
                }if(j == 3 && (i==11 || i==17 || i==18 || i==19 || i==20 || i==22 || i==23 || i==24|| i==25 || i==26)){
                    tiles[i][j] = wall;
                }if(j == 4 && (i==5||i==6||i==7||i==9||i==10||i==11||i==17||i==26)){
                    tiles[i][j] = wall;
                }if(j == 5 && (i == 1 || i== 2 || i == 3 || i == 4)){
                    tiles[i][j] = wall;
                }if(j == 6 && (i == 5 || i== 6 || i == 7 || i == 9|| i == 10|| i == 11|| i == 13|| i == 14|| i == 15|| i == 16|| i == 17)){
                    tiles[i][j] = wall;
                }
                if(j == 7 && (i == 5 || i== 13 || i == 17)){
                    tiles[i][j] = wall;
                }
                if(j == 8 && (i == 17 || i== 18 || i == 20 || i == 21|| i == 22|| i == 25|| i == 26)){
                    tiles[i][j] = wall;
                }
                if(j == 9 && (i == 5 || i== 13 || i == 22 || i == 26)){
                    tiles[i][j] = wall;
                }
                if(j == 10 && (i == 2 || i== 3 || i == 5 || i == 6|| i == 7|| i == 8|| i == 13|| i == 26)){
                    tiles[i][j] = wall;
                }
                if(j == 11 && (i == 2 || i== 8 || i == 9 || i == 10|| i == 11|| i == 13|| i == 22)){
                    tiles[i][j] = wall;
                }
                if(j == 12 && (i == 2 || i== 22)){
                    tiles[i][j] = wall;
                }
                if(j == 13 && (i == 2 || i== 8 || i == 13 || i == 22)){
                    tiles[i][j] = wall;
                }
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setPotions(List<Potion> potions) {
        this.potions = potions;
    }
    public List<Entity> getItems() {
        return items;
    }

    public void addToItems(List<Entity> items){
        this.items.addAll(items);
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
