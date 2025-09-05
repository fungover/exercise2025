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
        if(pos == null || pos.length > 2){
            throw new IllegalArgumentException("Position must be a 2-element array [x,y].");
        }
        if(pos[0] < 0 || pos[0] >= tiles.length || pos[1] < 0 || pos[1] >= tiles[0].length){
            throw new IllegalArgumentException("Position out of bounds. [" + pos[0] + ", " + pos[1] + "]");
        }
        Tile tile = new Tile(type);
        tile.setPosition(pos);
        tiles[pos[0]][pos[1]] = tile;
    }
    private void setWall(int i, int j){
        Tile tile = new Tile("Wall");
        tile.setPosition(new int[]{i, j});
        tiles[i][j] = tile;
    }
    private void placeWalls(){
        //Tile wall = new Tile("Wall");
        for(int i=0; i<tiles.length; i++){
            for(int j=0; j<tiles[i].length; j++){
                if(j == 0 || j == 14 || i == 0 || i == 29){
                    setWall(i, j);
                }if(j == 1 && i == 11){
                    setWall(i, j);
                }if(j == 2 && i==17){
                    setWall(i, j);
                }if(j == 3 && (i==11 || i==17 || i==18 || i==19 || i==20 || i==22 || i==23 || i==24|| i==25 || i==26)){
                    setWall(i, j);
                }if(j == 4 && (i==5||i==6||i==7||i==9||i==10||i==11||i==17||i==26)){
                    setWall(i, j);
                }if(j == 5 && (i == 1 || i== 2 || i == 3 || i == 4)){
                    setWall(i, j);
                }if(j == 6 && (i == 5 || i== 6 || i == 7 || i == 9|| i == 10|| i == 11|| i == 13|| i == 14|| i == 15|| i == 16|| i == 17)){
                    setWall(i, j);
                }
                if(j == 7 && (i == 5 || i== 13 || i == 17)){
                    setWall(i, j);
                }
                if(j == 8 && (i == 17 || i== 18 || i == 20 || i == 21|| i == 22|| i == 25|| i == 26)){
                    setWall(i, j);
                }
                if(j == 9 && (i == 5 || i== 13 || i == 22 || i == 26)){
                    setWall(i, j);
                }
                if(j == 10 && (i == 2 || i== 3 || i == 5 || i == 6|| i == 7|| i == 8|| i == 13|| i == 26)){
                    setWall(i, j);
                }
                if(j == 11 && (i == 2 || i== 8 || i == 9 || i == 10|| i == 11|| i == 13|| i == 22)){
                    setWall(i, j);
                }
                if(j == 12 && (i == 2 || i== 22)){
                    setWall(i, j);
                }
                if(j == 13 && (i == 2 || i== 8 || i == 13 || i == 22)){
                    setWall(i, j);
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
