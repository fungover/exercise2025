package org.example.map;

import org.example.utils.Position;

/** Axis-aligned rectangular room with convenience methods. */
public record Room(int left, int top, int width, int height) {

    public int right() {
        return left + width  - 1;
    }

    public int bottom() {
        return top  + height - 1;
    }

    public int centerX() {
        return left + width  / 2;
    }

    public int centerY() {
        return top  + height / 2;
    }

    /** Returns true if this room intersects or is within 'padding' tiles of another room. */
    public boolean intersectsWith(Room other, int padding) {
        int expandedLeft = left - padding;
        int expandedTop = top - padding;
        int expandedRight = right() + padding;
        int expandedBottom = bottom() + padding;

        // If they are clearly separated horizontally or vertically, they do not intersect.
        boolean separatedHorizontally = expandedRight < other.left || other.right() < expandedLeft;
        boolean separatedVertically = expandedBottom < other.top || other.bottom() < expandedTop;
        return !(separatedHorizontally || separatedVertically);
    }
}
