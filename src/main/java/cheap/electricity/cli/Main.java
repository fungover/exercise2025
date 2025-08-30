package cheap.electricity.cli;

import cheap.electricity.services.Api;
import cheap.electricity.services.UrlFormatter;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Let's check when it's better to start charging");
    System.out.println("Press y to start");

    String input = System.console().readLine();
    if(input.equals("y")){
      UrlFormatter day = new UrlFormatter("SE2");
      String url = day.formatUrl();

      Api api = new Api(url);
      showMenu(api, day);
    }
  }

  static void showMenu(Api api, UrlFormatter day) throws IOException, InterruptedException {
    System.out.println("-----");
    System.out.println("Choose from the menu:");
    System.out.println("1. Download prices(day1, day2)");
    System.out.println("2. Show mean price(24h)");
    System.out.println("3. Show price highest and lowest(h)");
    System.out.println("4. The best time to charge a car");
    System.out.println("6. Choose zon(default is: ZONE2)");
    System.out.println("7. Exit");

    String input = System.console().readLine();
    switch(input) {
      case "1":
        api.getPrices();
        showMenu(api, day);
        break;
      case "2":
        api.showMeanPrice();
        showMenu(api, day);
        break;
      case "6":
        System.out.println("Current zone is: " + day.getZone());
        System.out.println("""
                        Zones available:
                        1. SE1 = Luleå / Norra Sverige
                        2. SE2 = Sundsvall / Norra Mellansverige
                        3. SE3 = Stockholm / Södra Mellansverige
                        4. SE4 = Malmö / Södra Sverige\\s""\")
                        5. Cancel
                        """);
        String input2 = System.console().readLine();
        switch(input2) {
          case "1":
            day.setZone("SE1");
            System.out.println("Zone set to SE1");
            api.setUrl(day.formatUrl());
            showMenu(api, day);
            break;
            case "2":
              day.setZone("SE2");
              System.out.println("Zone set to SE2");
              api.setUrl(day.formatUrl());
              showMenu(api, day);
              break;
              case "3":
                day.setZone("SE3");
                System.out.println("Zone set to SE3");
                api.setUrl(day.formatUrl());
                showMenu(api, day);
                break;
                case "4":
                  day.setZone("SE4");
                  System.out.println("Zone set to SE4");
                  api.setUrl(day.formatUrl());
                  showMenu(api, day);
                  break;
      }
        break;
      case "7":
        return;
      default:
        System.out.println("Invalid option");
        showMenu(api, day);
    }
  }
}
