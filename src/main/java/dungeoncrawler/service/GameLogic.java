package dungeoncrawler.service;

import dungeoncrawler.enteties.*;
import dungeoncrawler.game.Game;
import dungeoncrawler.game.InputHandler;
import dungeoncrawler.map.Dungeon;
import dungeoncrawler.map.Tile;
import dungeoncrawler.utils.Message;

import java.util.List;

import static dungeoncrawler.utils.Randomizer.randomNumber;

public class GameLogic {
    Dungeon dungeon;
    Player player;
    InputHandler inputHandler;
    Movement movement;
    Combat combat;
    Game game;
    Message message;
    boolean running = true;

    public GameLogic(Dungeon dungeon, Player player, Game game) {
        this.game = game;
        this.dungeon = dungeon;
        this.player = player;
        inputHandler = new InputHandler();
        movement = new Movement();
        combat = new Combat(this, movement);
        message = new Message();
    }

    public void runGame(){
        while(running) {
            Tile tile = getSurroundings(player.getPosition());
            if (tile == null) {
                message.send("You can't see anything. Try walking around a bit. (Go north/east/south/west)");
                String input = inputHandler.handleMovementInput().toLowerCase();
                switch (input) {
                    case "go north": movement.goNorth(player);
                        break;
                    case "go south": movement.goSouth(player);
                        break;
                    case "go east": movement.goEast(player);
                        break;
                    case "go west": movement.goWest(player);
                        break;
                }
            } else if (tile.getType().equals("Troll")||tile.getType().equals("Giant")) {
                startFight(tile);
            } else if(tile.getType().equals("Wall")) {
                message.send("You almost walked in to a wall, try going in another direction.");
                movement.returnToPrevious(player);
            } else if(tile.getType().equals("Potion")){
                message.send("You found a potion. Do you want to drink it? (Yes/No");
                String input = inputHandler.handleYesNoInput();
                if(input.equalsIgnoreCase("yes")){
                    takeItem(tile);
                }else{
                    movement.returnToPrevious(player);
                }
            }else if(tile.getType().equals("Sword")){
                message.send("You found a sword on the ground. It will make your attacks 40% stronger");
                message.send("If you currently have a weapon you have to drop it to pick up a new one.");
                message.send("Do you want to pick it up= (Yes/No)");
                String input = inputHandler.handleYesNoInput();
                if(input.equalsIgnoreCase("yes")){
                    takeItem(tile);
                }else{
                    movement.returnToPrevious(player);
                }
            }else if(tile.getType().equals("Dagger")){
                message.send("You found a dagger on the ground. It will make your attacks 20% stronger.");
                message.send("If you currently have a weapon you have to drop it to pick up a new one.");
                message.send("Do you want to pick it up= (Yes/No)");
                String input = inputHandler.handleYesNoInput();
                if(input.equalsIgnoreCase("yes")){
                    takeItem(tile);
                }else{
                    movement.returnToPrevious(player);
                }
            }
        }
    }
    public void takeItem(Tile tile){
        int strength;
        int oldWeapon;
        switch (tile.getType()) {
            case "Potion":
                int hp = randomNumber(5,20);

                message.send("The potion gives you +" + hp + "hp.");
                int newHp = player.getHp()+hp;
                player.setHp(newHp);
                removeItem(tile);
                break;
            case "Sword":
                oldWeapon = player.getWeaponStrength();
                player.setWeaponStrength(4);
                strength = player.getTotalStrength()-oldWeapon+player.getWeaponStrength();
                player.setTotalStrength(strength);
                removeItem(tile);
                break;
            case "Dagger":
                oldWeapon = player.getWeaponStrength();
                player.setWeaponStrength(2);
                strength = player.getTotalStrength()-oldWeapon+player.getWeaponStrength();
                player.setTotalStrength(strength);
                removeItem(tile);
                break;
        }
    }
    public void removeItem(Tile tile){
        int[] pos = tile.getPosition();
        Tile[][] tiles = dungeon.getTiles();
        tiles[pos[0]][pos[1]] = null;
        List<Entity> itemList = dungeon.getItems();
        itemList.removeIf(e -> java.util.Arrays.equals(e.getPosition(), pos));
    }

    public void startFight(Tile tile){
        String monsterType = tile.getType();
        message.send("You have found a "+monsterType+". Do you want to run, hide or fight?");
        Enemy enemy;
        if(tile.getType().equals("Troll")){
            enemy = new Troll(tile.getPosition());
        }else{
            enemy = new Giant(tile.getPosition());
        }
        while(true) {
            int fighting = combat.playerTurn(inputHandler, player, enemy, tile);
            if(fighting == 1) {
                defeatedEnemy(enemy);
                break;
            }else if(fighting == 2) {
                break;
            }
            boolean alive = combat.enemyTurn(player, enemy);
            if(!alive) {
                die();
                break;
            }
        }
    }
    public Tile getSurroundings(int[] position){
        message.send("Looking at surroundings...");
        Tile[][] tiles = dungeon.getTiles();
        return tiles[position[0]][position[1]];
    }

    public void die(){
        message.send("You lost.");
        message.send("Do you want to play again? (Yes/No)");
        String replay = inputHandler.handleYesNoInput();
        if(replay.equalsIgnoreCase("yes")){
            message.send("Replay game");
            running = false;
            newGame();
        }else{
            running = false;
        }
    }
    public void win(){
        message.send("Congratulations! You defeated every enemy in the dungeon!");
        message.send("Do you want to play again? (Yes/No)");
        String replay = inputHandler.handleYesNoInput();
        if(replay.equals("yes")){
            message.send("Replay game");
            running = false;
            newGame();
        }else{
            running = false;
        }
    }

    public void defeatedEnemy(Enemy enemy){
        message.send("You defeated the monster.");
        int[] pos = enemy.getPosition();
        Tile[][] tiles = dungeon.getTiles();
        tiles[pos[0]][pos[1]] = null;
        List<Enemy> enemyList = dungeon.getEnemies();
        enemyList.removeIf(e -> java.util.Arrays.equals(e.getPosition(), pos));
        if(enemyList.isEmpty()){
            win();
        }
    }
    private void newGame(){
        game.setEnemies(null);
        game.setDungeon(null);
        game.setPlayer(null);
        game.setGameLogic(null);
        this.game = new Game();
        this.game.start();
    }
}
