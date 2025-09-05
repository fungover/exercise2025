package dungeoncrawler.service;

import dungeoncrawler.enteties.Player;

public class Movement {

    public void goNorth(Player player){
        move(player, 0, -1);
    }
    public void goSouth(Player player){
        move(player, 0, 1);
    }
    public void goEast(Player player){
        move(player, 1, 0);
    }
    public void goWest(Player player){
        move(player, -1, 0);
    }
    private void move(Player player, int deltaX, int deltaY){
        int[] pos = player.getPosition();
        int[] prevPos = pos.clone();
        player.setPreviousPosition(prevPos);
        pos[0] += deltaX;
        pos[1] += deltaY;
        player.setPosition(pos);
    }
    public void returnToPrevious(Player player){
        int[] prev = player.getPreviousPosition();
        player.setPosition(prev);
    }
}
