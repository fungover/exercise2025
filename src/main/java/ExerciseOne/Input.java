package ExerciseOne;

import java.util.Locale;
import java.util.Scanner;

public class Input {

    public int areaInput(){
        Scanner input = new Scanner(System.in);

        int area;

        while(true){
            System.out.println("""
                PRISKLASS:
                    1 = Luleå / Norra Sverige
                    2 = Sundsvall / Norra Mellansverige
                    3 = Stockholm / Södra Mellansverige
                    4 = Malmö / Södra Sverige
               """);
            System.out.print("Ange prisklass för det område som du vill beräkna elpriset för (1-4): ");

            if(input.hasNextInt()){
                area = input.nextInt();

                if(area >= 1 && area <= 4)return area;

                System.out.println("Ogiltigt val. Ange en siffra 1-4: ");
            }else{
                System.out.println("Ogiltigt inmatning. Ange en siffra 1-4: ");
                input.nextLine();
            }
        }
    }

    public boolean calculate(){
        Scanner input = new Scanner(System.in);
        String answer;

        while (true){
            System.out.print("Vill du välja csv fil för att beräkna kostnad av elförbrukning Y/N: ");
            answer = input.next().toLowerCase(Locale.ROOT);
            if (answer.equals("y") || answer.equals("n")) break;
            System.out.println("Ange svar med Y eller N");
        }

        return answer.equals("y");

    }

    public String  pathToFile(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Ange sökväg till csv fil: ");
        return sc.nextLine();
    }
}
