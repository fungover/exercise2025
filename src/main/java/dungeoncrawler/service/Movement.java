package dungeoncrawler.service;

import dungeoncrawler.enteties.Player;

public class Movement {

    public void goNorth(Player player){
        int[] pos = player.getPosition();
        int[] prevPos = pos.clone();
        player.setPreviousPosition(prevPos);
        pos[1] -= 1;
        player.setPosition(pos);
    }
    public void goSouth(Player player){
        int[] pos = player.getPosition();
        int[] prevPos = pos.clone();
        player.setPreviousPosition(prevPos);
        pos[1] += 1;
        player.setPosition(pos);
    }
    public void goEast(Player player){
        int[] pos = player.getPosition();
        int[] prevPos = pos.clone();
        player.setPreviousPosition(prevPos);
        pos[0] += 1;
        player.setPosition(pos);
    }
    public void goWest(Player player){
        int[] pos = player.getPosition();
        int[] prevPos = pos.clone();
        player.setPreviousPosition(prevPos);
        pos[0] -= 1;
        player.setPosition(pos);
    }
    public void returnToPrevious(Player player){
        int[] prev = player.getPreviousPosition();
        player.setPosition(prev);
    }
}
