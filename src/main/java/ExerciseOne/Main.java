package ExerciseOne;

import java.io.IOException;
import java.util.List;

public class Main {

    static void main() throws IOException, InterruptedException {

        Input input = new Input();
        int area = input.areaInput();

        GetDateAndTime gd = new GetDateAndTime();
        String today = gd.getToday(area);


        Request req = new Request();
        HandleList hl = new HandleList();

        hl.addList(req.request(today));

        int timeOfToday = gd.getHour();


        if(timeOfToday >= 13){
           String tomorrow = gd.getTomorrow(area);
            hl.addList(req.request(tomorrow));
        }

        Calculate calc = new Calculate();
        calc.calculateMean(req.request(today));

        RemoveHours hr = new RemoveHours();
        List<Pricing> listHoursRemoved = hr.removePastTime(hl.getListOfPrices());

        calc.calculateMin(listHoursRemoved);
        calc.calculateMax(listHoursRemoved);
        calc.calculateCheapest(listHoursRemoved, 2);
        calc.calculateCheapest(listHoursRemoved, 4);
        calc.calculateCheapest(listHoursRemoved, 8);

        boolean check = input.calculate();

        if(check){
            ReadFile rf = new ReadFile();
            calc.costOfConsumption(rf.readFile(), listHoursRemoved);
        }
    }
}

