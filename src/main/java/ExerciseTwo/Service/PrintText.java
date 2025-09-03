package ExerciseTwo.Service;

public class PrintText {

    private static final String red = "\u001B[31m";
    private static final String green =  "\u001B[32m";
    private static final String original = "\u001B[0m";
    private static final String bold = "\u001B[1m";

    public static void printRed(String text){
        System.out.println(red+text+original);
    }

    public static void printGreen(String text){
        System.out.println(green+text+original);
    }

    public static void printBold(String text){
        System.out.println(bold+text+original);
    }

}
