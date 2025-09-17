package interfaces;

/**
 * Interface for objects that have position
 */
public interface Positionable {
    int getX();
    int getY();
    void setPosition(int x, int y);
}