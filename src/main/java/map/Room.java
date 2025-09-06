package map;

import utils.Position;

public class Room {
    private final Position topLeft;
    private final int width;
    private final int height;

    public Room(int x, int y, int width, int height) {
        topLeft = new Position(x, y);
        this.width = width;
        this.height = height;
    }

    public int left() {
        return topLeft.getX();
    }

    public int top() {
        return topLeft.getY();
    }

    public int right() {
        return left() + width - 1;
    }

    public int bottom() {
        return top() + height - 1;
    }

    // Use integer division to always get a valid tile.
    public Position center() {
        return new Position((left() + right()) / 2, (top() + bottom()) / 2);
    }

    public boolean isOverlapping(Room o) {
        return !(right() < o.left() || o.right() < left()
                || bottom() < o.top() || o.bottom() < top());
    }

    public boolean isCoordinateInRoom(int x, int y) {
        return x >= left() && x <= right() && y >= top() && y <= bottom();
    }
}
