package dungeoncrawler.game;
import dungeoncrawler.enteties.*;
import dungeoncrawler.map.Dungeon;
import dungeoncrawler.map.Tile;
import dungeoncrawler.service.GameLogic;
import dungeoncrawler.utils.Message;
import dungeoncrawler.utils.Randomizer;
import java.util.ArrayList;
import java.util.List;

import static dungeoncrawler.utils.Randomizer.randomNumber;

public class Game {
    Player player;
    Dungeon dungeon;
    GameLogic gameLogic;
    Message message;
    List<Enemy> enemies;
    List<Potion> potions;
    List<Weapon> weapons;
    List<Entity> items;
    InputHandler inputHandler;

    public Game() {
        message = new Message();
        inputHandler = new InputHandler();
        dungeon = new Dungeon();
        enemies = new ArrayList<>();
        potions = new ArrayList<>();
        weapons = new ArrayList<>();
        items = new ArrayList<>();
    }

    public void start() {
        message.send("Welcome, adventurer!");
        message.send("What is your name?");
        String name = inputHandler.handleInput();
        message.send("You are in a dark, damp dungeon armed with nothing but your fighting skill.");
        message.send("Who knows what dangers and treasures can be lurking around the corner?");
        getEnemies(randomNumber(12, 30));
        getItems(randomNumber(12, 30));
        int[] pos = getPosition();
        player = new Player(name, 30, pos);
        gameLogic = new GameLogic(dungeon, player,this);
        gameLogic.runGame();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    private int[] getPosition(){
        while(true){

            Tile[][] tiles = dungeon.getTiles();
            int x = randomNumber(0, tiles.length-1);
            int y = randomNumber(0, tiles[0].length-1);
            if(tiles[x][y] == null){
                return new int[]{x,y};
            }
        }
    }
    private void getEnemies(int nbrEnemies){
        int nbrTrolls = randomNumber(1,nbrEnemies);;
        for(int i = 0; i < nbrTrolls; i++){
            int[] pos = getPosition();
            Enemy enemy = new Troll(pos);
            enemies.add(enemy);
            dungeon.placeEntity("Troll", pos);
        }
        int nbrGiants = nbrEnemies - nbrTrolls;
        for(int i = 0; i < nbrGiants; i++){
            int[] pos = getPosition();
            Enemy enemy  = new Giant(pos);
            enemies.add(enemy);
            dungeon.placeEntity("Giant", pos);
        }
        dungeon.setEnemies(enemies);
    }
    private void getItems(int nbrItems){
        int nbrPotions = randomNumber(1, nbrItems);
        for(int i = 0; i < nbrPotions; i++){
            int[] pos = getPosition();
            Potion potion = new Potion(pos);
            items.add(potion);
            potions.add(potion);
            dungeon.placeEntity("Potion", pos);
        }
        dungeon.setPotions(potions);
        int remainingItems = nbrItems - nbrPotions;
        if(remainingItems <= 0){
            return;
        }
        int nbrDaggers = randomNumber(1, remainingItems);
        for(int i = 0; i < nbrDaggers; i++){
            int[] pos = getPosition();
            Weapon dagger = new Weapon("Dagger",2, pos);
            weapons.add(dagger);
            items.add(dagger);
            dungeon.placeEntity("Dagger", pos);
        }
        int nbrSwords = nbrItems - nbrDaggers;
        if(nbrSwords < 0){
            nbrSwords = 0;
        }
        for(int i = 0; i < nbrSwords; i++){
            int[] pos = getPosition();
            Weapon sword = new Weapon("Sword",4, pos);
            weapons.add(sword);
            items.add(sword);
            dungeon.placeEntity("Sword", pos);
        }
        dungeon.setWeapons(weapons);
        dungeon.addToItems(items);
    }
}

