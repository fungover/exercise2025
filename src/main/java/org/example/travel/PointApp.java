import org.example.travel.NamedPoint;
import org.example.travel.Point;
import org.example.travel.PointGroup;

void main() {
    PointGroup pointGroup = new PointGroup();

    Object obj = new Point(10, 20);
    if( obj instanceof Point point)
        System.out.println(point.display());
    Integer
    Point point1 = new Point(10, 20);
    Point point2 = new Point(20, 10);
    NamedPoint namedPoint1 = new NamedPoint("Point 1", 100,100);

    //System.out.println(namedPoint1.display());

    pointGroup.addPoint(namedPoint1);
    pointGroup.addPoint(point1);
    pointGroup.addPoint(point2);
    pointGroup.addPoint(30,30);

    pointGroup.displayAll();

    System.out.println("Max distance: " + pointGroup.findMaxDistance());
}