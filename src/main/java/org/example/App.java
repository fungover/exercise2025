import org.example.Order;
import org.example.Rectangle;
import java.awt.Color;

void main() {
    System.out.println("Hello World!");
    Rectangle rectangle = new Rectangle();

    Integer integer = Integer.valueOf(10);
    //Auto Boxing
    Integer integer2 = 10;
    //Auto Unboxing
    int i = integer;

    rectangle.setWidth(10);
    System.out.println(rectangle.getWidth());
    System.out.println(rectangle.getHeight());
    System.out.println(rectangle.getColor());

    Order order = Order.createOrder(123);
}

