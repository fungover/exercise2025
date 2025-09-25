package org.example.utils;
import org.example.entities.Character;
import java.util.List;

public class Helpers {
    public static boolean containsEnemyAt(int x, int y, List<Character> enemies) {
        for (Character e : enemies) {
            if (e.getX() == x && e.getY() == y) {
                return true;
            }
        }
        return false;
    }
    public static boolean containsItemAt(int x, int y, List<ItemOnMap> items) {
        for (ItemOnMap i : items) {
            if (i.x == x && i.y == y) {
                return true;
            }
        }
        return false;
    }
}
