package dungeoncrawler.service;
import dungeoncrawler.utils.Message;
import dungeoncrawler.enteties.Enemy;
import dungeoncrawler.enteties.Player;
import dungeoncrawler.game.InputHandler;
import dungeoncrawler.map.Tile;
import static dungeoncrawler.utils.Randomizer.randomNumber;

public class Combat {
    GameLogic gameLogic;
    Movement movement;
    Message message;

    public Combat(GameLogic gameLogic, Movement movement) {
        this.gameLogic = gameLogic;
        this.movement = movement;
        message = new Message();
    }


    public int playerTurn(InputHandler inputHandler, Player player, Enemy enemy, Tile tile) {
        String input = inputHandler.handleCombatInput();
        int result;
        int number = randomNumber(0,player.getTotalStrength());
        result = switch (input) {
            case "run" -> run(player);
            case "fight" -> fight(enemy, number);
            case "hide" -> hide(player, number);
            default -> 4;
        };
        return result;
    }
    public boolean enemyTurn(Player player, Enemy enemy){
        int number = randomNumber(0,enemy.getStrength());
        if(number >= 5){
            player.setDamage(10);
            message.send("The enemy attacked you!");
            if(player.getHp()- player.getDamage() <= 0){
                message.send("You died.");
                return false;
            }
        }else{
            message.send("The enemy tried to attack you, but missed");
        }
        return true;
    }
    public int fight(Enemy enemy, int luckyNumber){
        if(luckyNumber >= 5){
            enemy.setDamage(10);
            message.send("The attack hit!");
            if(enemy.getHp() - enemy.getDamage() <= 0){
                message.send("Enemy defeated!");
                return 1;
            }
        }else{
            message.send("You missed.");
        }
        return 4;
    }
    public int hide(Player player, int luckyNumber){
        if(luckyNumber >= 5){
            message.send("You hid successfully and are now backing away silently.");
            movement.returnToPrevious(player);
            return 2;
        }else{
            message.send("The monster can still see you");
            return 4;
        }
    }

    public int run(Player player){
        message.send("You managed to run away from the monster");
        movement.returnToPrevious(player);
        return 2;
    }
}
