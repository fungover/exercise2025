package org.example.travel;

import java.util.ArrayList;
import java.util.List;

public class PointGroup {
    //We should be able to calculate the distance between all points in the group as a path.
    //Find the longest distance between two points in the group.. Komplexitet O(n²)

    private final List<Point> points = new ArrayList<>();

    public void addPoint(int x, int y){
        var point = new Point(x,y);
        points.add(point);
    }

    public void addPoint(Point point){
        points.add(point);
    }

    public void displayAll() {
        for (Point point : points) {
            System.out.println(point.display());
        }
    }

    public double findMaxDistance() {
        double maxDistance = 0.0;
        for (int i = 0; i < points.size(); i++) {
            Point point1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point point2 = points.get(j);
                var distance = point1.distanceTo(point2);
                if (distance > maxDistance)
                    maxDistance = distance;
            }
        }
        return maxDistance;
    }
}
