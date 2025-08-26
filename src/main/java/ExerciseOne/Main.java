package ExerciseOne;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static void main() throws IOException, InterruptedException {

        Input input = new Input();
        int area = input.areaInput();

        GetDate gd = new GetDate();
        String today = gd.geToday(area);

        Request req = new Request();
        List<Pricing> pricing = new ArrayList<>(req.request(today));

        int timeOfToday = gd.getTime();

        if(timeOfToday >= 13){
            String tomorrow = gd.geToday(area);
            pricing.addAll(req.request(tomorrow));
        }

        Calculate calc = new Calculate(pricing);
        System.out.println("LIST"+pricing);

    }
}

