package ExerciseOne;

import java.io.IOException;

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

        hl.removePastTime();
        Calculate calc = new Calculate();
        calc.calculateMean(hl.getListOfPrices());
        calc.calculateMin(hl.getListOfPrices());
        calc.calculateMax(hl.getListOfPrices());
        calc.calculateCheapest(hl.getListOfPrices(), 2);
        calc.calculateCheapest(hl.getListOfPrices(), 4);
        calc.calculateCheapest(hl.getListOfPrices(), 8);


    }
}

