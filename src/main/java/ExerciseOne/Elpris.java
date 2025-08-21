package ExerciseOne;


import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Elpris {

    static void main() {

        Scanner input = new Scanner(System.in);

        int area = 0;
        boolean correctInput = true;

        do{
            System.out.println("""
                PRISKLASS:
                    1 = Luleå / Norra Sverige
                    2 = Sundsvall / Norra Mellansverige
                    3 = Stockholm / Södra Mellansverige
                    4 = Malmö / Södra Sverige
               """);
            System.out.print("Ange vilken prisklass du vill beräkna elpriset för: ");
            area = input.nextInt();

            if(area == 1 || area == 2 || area == 3 || area == 4){
                correctInput = false;
            }

        }while (correctInput);

        Request req = new Request();
        req.request(area);
    }
}



