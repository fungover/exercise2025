package ExerciseTwo.Utils;

public class PrintText {

    private static final String red = "\u001B[31m";
    private static final String green =  "\u001B[32m";
    private static final String blue = "\u001B[34m";
    private static final String yellow = "\u001B[33m";
    private static final String original = "\u001B[0m";
    private static final String bold = "\u001B[1m";

    public static void printRed(String text){
        System.out.println(red+text+original);
    }

    public static void printGreen(String text){
        System.out.println(green+text+original);
    }

    public static void printBlue(String text){
        System.out.println(blue+text+original);
    }

    public static void printYellow(String text){
        System.out.println(yellow+text+original);
    }

    public static void printBold(String text){
        System.out.println(bold+text+original);
    }

}
