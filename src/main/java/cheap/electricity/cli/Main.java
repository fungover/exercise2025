package cheap.electricity.cli;

public class Main {
  public static void main(String[] args) {
    System.out.println("Let's check when it's better to start charging");
    System.out.println("Press y to start");

    String input = System.console().readLine();
    if(input.equals("y")){
      showMenu();
    }
  }

  static void showMenu(){
    System.out.println("Choose from the menu:");
    System.out.println("1. Download prices(day1, day2)");
    System.out.println("2. Show mean price(24h)");
    System.out.println("3. Show price highest and lowest(h)");
    System.out.println("4. The best time to charge a car");
    System.out.println("6. Choose zon(default is: ZONE2)");
    System.out.println("7. Exit");
  }
}
