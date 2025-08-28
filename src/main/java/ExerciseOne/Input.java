package ExerciseOne;

import java.util.Scanner;

public class Input {

    public int areaInput(){
        Scanner input = new Scanner(System.in);

        int area;
        boolean correctInput = true;

        do{
            System.out.println("""
                PRISKLASS:
                    1 = Luleå / Norra Sverige
                    2 = Sundsvall / Norra Mellansverige
                    3 = Stockholm / Södra Mellansverige
                    4 = Malmö / Södra Sverige
               """);
            System.out.print("Ange prisklass för det område som du vill beräkna elpriset för: ");
            area = input.nextInt();

            if(area == 1 || area == 2 || area == 3 || area == 4){
                correctInput = false;
            }

        }while (correctInput);

        return area;
    }
}
