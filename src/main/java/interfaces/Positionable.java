package interfaces;

/**
 * Interface för objekt som har position
 */
public interface Positionable {
    int getX();
    int getY();
    void setPosition(int x, int y);
}