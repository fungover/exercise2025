import org.example.travel.Point;
import org.example.travel.PointGroup;

void main() {

    PointGroup pointGroup = new PointGroup();

    Point point1 = new Point(10, 20);
    Point point2 = new Point(20, 10);

    pointGroup.addPoint(point1);
    pointGroup.addPoint(point2);
    pointGroup.addPoint(30,30);

    pointGroup.displayAll();

    System.out.println("Max distance: " + pointGroup.findMaxDistance());
}